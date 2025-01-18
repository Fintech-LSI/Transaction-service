package com.lsi.transaction.service;

import com.lsi.transaction.dto.request.NotificationRequest;
import com.lsi.transaction.entity.*;
import com.lsi.transaction.repository.DepositRepository;
import com.lsi.transaction.repository.TransactionRepository;
import com.lsi.transaction.repository.TransferRepository;
import com.lsi.transaction.repository.WithDrawRepository;
import com.lsi.transaction.service.feign_clients.WalletFeignClient;
import com.lsi.transaction.service.kafka_service.TransactionProducerService;
import org.hibernate.TransactionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService {
  private final TransactionRepository transactionRepository;
  private final TransferRepository transferRepository;
  private final DepositRepository depositRepository;
  private final WithDrawRepository withDrawRepository;

  private final TransactionProducerService transactionProducerService;
  private final WalletFeignClient walletFeignClient;

  public TransactionService(
          WithDrawRepository withDrawRepository,
          TransactionRepository transactionRepository,
          TransferRepository transferRepository,
          DepositRepository depositRepository,
          TransactionProducerService transactionProducerService,
          WalletFeignClient walletFeignClient
  ) {
      this.transferRepository = transferRepository;
      this.depositRepository = depositRepository;
      this.withDrawRepository = withDrawRepository;
      this.transactionRepository = transactionRepository;
      this.transactionProducerService = transactionProducerService ;
      this.walletFeignClient = walletFeignClient;
  }


  public void createWithdraw(WithDraw withdraw) {
      try {
          // Set initial status
          withdraw.setStatus(TransactionStatus.PENDING);

          // Save the transfer record
          WithDraw savedWithDraw = withDrawRepository.save(withdraw);

          // Update status to SUCCESS after saving
          savedWithDraw.setStatus(TransactionStatus.SUCCESS);
          withDrawRepository.save(savedWithDraw);

        createNotification(
          "Your transaction has been processed:: withdraw "+withdraw.getAmount()
            +" in "+withdraw.getWalletId(),
          withdraw.getWalletId()
        );

          // Could implement compensation/rollback logic here if needed
      } catch (Exception e) {
          withdraw.setStatus(TransactionStatus.FAILED);
          withDrawRepository.save(withdraw);
          // Could implement retry logic or notification system
          throw new TransactionException("Failed to process WithDraw ", e);
      }
  }

  public void createTransfer(Transfer transfer ) {
      try {
          // Set initial status
          transfer.setStatus(TransactionStatus.PENDING);

          // Save the transfer record
          Transfer savedTransfer = transferRepository.save(transfer);

          // Update status to SUCCESS after saving
          savedTransfer.setStatus(TransactionStatus.SUCCESS);
          transferRepository.save(savedTransfer);

        createNotification(
          "Your transaction has been processed:: transfer "+transfer.getAmount()
            +" from "+transfer.getWalletId()+" to "+transfer.getTargetWalletId(),
          transfer.getWalletId()
        );


        createNotification(
          "Your transaction has been processed:: received "+transfer.getAmount()
            +" from "+transfer.getWalletId(),
          transfer.getTargetWalletId()
        );


      } catch (Exception e) {
          transfer.setStatus(TransactionStatus.FAILED);
          transferRepository.save(transfer);
          // Could implement retry logic or notification system
          throw new TransactionException("Failed to process transfer", e);
      }
  }

  public void createDeposit(Deposit deposit) {
      try {
          // Set initial status
          deposit.setStatus(TransactionStatus.PENDING);

          // Save the transfer record
          Deposit savedDeposit = depositRepository.save(deposit);

          // Update status to SUCCESS after saving
          savedDeposit.setStatus(TransactionStatus.SUCCESS);
          depositRepository.save(savedDeposit);

          createNotification(
            "Your transaction has been processed:: deposit "+deposit.getAmount()
              +" in "+deposit.getWalletId(),
            deposit.getWalletId()
          );


        // Could implement compensation/rollback logic here if needed
      } catch (Exception e) {
          deposit.setStatus(TransactionStatus.FAILED);
          depositRepository.save(deposit);
          // Could implement retry logic or notification system
          throw new TransactionException("Failed to process Deposit", e);
      }
  }




  public List<WithDraw> listWithdraws() {
    return withDrawRepository.findAll();
  }

  public List<Transfer> listTransfers() {
      return transferRepository.findAll();
  }

  private void createNotification(String message , Long walletId) {
    NotificationRequest notificationRequest2 = NotificationRequest.builder()
      .userId(walletFeignClient.getUser(walletId))
      .message(message)
      .build();

    transactionProducerService.sendNotificationRequests(notificationRequest2);
  }

  // List all transactions
  public List<Transaction> listTransactions() {
    return transactionRepository.findAll();
  }

  // List all transactions of a specific type (e.g., Withdrawal, Deposit, Transfer)
  public List<Transaction> listTransactionsByType(Class<? extends Transaction> type) {
    return transactionRepository.findByType(type);
  }

  // List all transactions for a specific wallet
  public List<Transaction> listTransactionsByWalletId(Long walletId) {
    return transactionRepository.findByWalletId(walletId);
  }

  // List all failed transactions
  public List<Transaction> listFailedTransactions() {
    return transactionRepository.findByStatus(TransactionStatus.FAILED);
  }

  // List all successful transactions
  public List<Transaction> listSuccessfulTransactions() {
    return transactionRepository.findByStatus(TransactionStatus.SUCCESS);
  }

  // List all pending transactions
  public List<Transaction> listPendingTransactions() {
    return transactionRepository.findByStatus(TransactionStatus.PENDING);
  }

}
