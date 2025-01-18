package com.lsi.transaction.service.kafka_service;


import com.lsi.transaction.dto.request.NotificationRequest;
import com.lsi.transaction.dto.request.TransactionRequest;
import com.lsi.transaction.entity.Deposit;
import com.lsi.transaction.entity.MoneyMethods;
import com.lsi.transaction.entity.Transfer;
import com.lsi.transaction.entity.WithDraw;
import com.lsi.transaction.service.TransactionService;
import com.lsi.transaction.service.feign_clients.WalletFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionConsumerService {

  private final TransactionService transactionService;
  private final TransactionProducerService transactionProducerService;
  private final WalletFeignClient walletFeignClient;
  @KafkaListener(
    topics = "transaction-requests",
    groupId = "transaction-service-group"
  )
  public void consumeTransactionRequest(TransactionRequest request) {
    try {
      log.info("Received transaction request: {}", request);

      switch (request.getTransactionType()) {
        case "TRANSFER":
          Transfer transfer = Transfer.builder()
            .walletId(request.getWalletId())
            .targetWalletId(request.getTargetWalletId())
            .amount(request.getAmount())
            .description(request.getDescription())
            .build();
          transactionService.createTransfer(transfer);
          NotificationRequest notificationRequest = NotificationRequest.builder()
            .userId(walletFeignClient.getUser(request.getWalletId()))
            .message("Your Transfer has been processed to wallet "+request.getTargetWalletId())
            .build();

          transactionProducerService.sendNotificationRequests(notificationRequest);




          break;

        case "DEPOSIT":
          Deposit deposit = Deposit.builder()
            .walletId(request.getWalletId())
            .amount(request.getAmount())
            .description(request.getDescription())
            .timestamp( LocalDateTime.now())
            .method(MoneyMethods.valueOf(request.getMoneyMethod()))
            .build();
          transactionService.createDeposit(deposit);
          break;

        case "WITHDRAW":
          WithDraw withdraw = WithDraw.builder()
            .walletId(request.getWalletId())
            .amount(request.getAmount())
            .description(request.getDescription())
            .build();
          transactionService.createWithdraw(withdraw);
          break;
      }
    } catch (Exception e) {
      // Implement error handling strategy
      // Could include: retry logic, dead letter queue, notification system
      log.error("Error processing transaction request: {}", e.getMessage());
    }
  }

  private void createNotification(String message , Long userId) {
    NotificationRequest notificationRequest2 = NotificationRequest.builder()
      .userId(walletFeignClient.getUser())
      .message(" you have received a transfer from wallet :"+request.getWalletId())
      .build();

    transactionProducerService.sendNotificationRequests(notificationRequest2);
  }
}
