package com.lsi.transaction.service;

import com.lsi.transaction.entity.Deposit;
import com.lsi.transaction.entity.TransactionStatus;
import com.lsi.transaction.entity.Transfer;
import com.lsi.transaction.entity.WithDraw;
import com.lsi.transaction.repository.DepositRepository;
import com.lsi.transaction.repository.TransactionRepository;
import com.lsi.transaction.repository.TransferRepository;
import com.lsi.transaction.repository.WithDrawRepository;
import org.hibernate.TransactionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService {
    private final TransferRepository transferRepository;
    private final DepositRepository depositRepository;
    private final WithDrawRepository withDrawRepository;

    public TransactionService(
            WithDrawRepository withDrawRepository,
            TransactionRepository transactionRepository,
            TransferRepository transferRepository,
            DepositRepository depositRepository
    ) {
        this.transferRepository = transferRepository;
        this.depositRepository = depositRepository;
        this.withDrawRepository = withDrawRepository;
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

            // Could implement compensation/rollback logic here if needed
        } catch (Exception e) {
            withdraw.setStatus(TransactionStatus.FAILED);
            withDrawRepository.save(withdraw);
            // Could implement retry logic or notification system
            throw new TransactionException("Failed to process WithDraw ", e);
        }
    }

    public void createTransfer(Transfer transfer) {
        try {
            // Set initial status
            transfer.setStatus(TransactionStatus.PENDING);

            // Save the transfer record
            Transfer savedTransfer = transferRepository.save(transfer);

            // Update status to SUCCESS after saving
            savedTransfer.setStatus(TransactionStatus.SUCCESS);
            transferRepository.save(savedTransfer);

            // Could implement compensation/rollback logic here if needed
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

            // Could implement compensation/rollback logic here if needed
        } catch (Exception e) {
            deposit.setStatus(TransactionStatus.FAILED);
            depositRepository.save(deposit);
            // Could implement retry logic or notification system
            throw new TransactionException("Failed to process Deposit", e);
        }
    }
}
