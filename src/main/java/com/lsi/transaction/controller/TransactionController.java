package com.lsi.transaction.controller;

import com.lsi.transaction.dto.request.NotificationRequest;
import com.lsi.transaction.entity.Deposit;
import com.lsi.transaction.entity.Transaction;
import com.lsi.transaction.entity.Transfer;
import com.lsi.transaction.entity.WithDraw;
import com.lsi.transaction.service.TransactionService;
import com.lsi.transaction.service.feign_clients.WalletFeignClient;
import com.lsi.transaction.service.kafka_service.TransactionProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  TransactionProducerService transactionProducerService;

  @Autowired
  WalletFeignClient walletFeignClient;

  @GetMapping("/test")
  public ResponseEntity<Long> test() {

//    NotificationRequest notificationRequest = new NotificationRequest();
//    notificationRequest.setMessage("test message");
//    notificationRequest.setUserId(8L);
//    notificationRequest.setRecipient("ahmed");
//    transactionProducerService.sendNotificationRequests(notificationRequest);
//
//    return ResponseEntity.ok("ok");

    return ResponseEntity.ok(walletFeignClient.getUser(1L));
  }


  // List all transactions
  @GetMapping
  public ResponseEntity<List<Transaction>> getAllTransactions() {
    return ResponseEntity.ok(transactionService.listTransactions());
  }

  // List all transactions of a specific type
  @GetMapping("/type/{type}")
  public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable String type) {
    Class<? extends Transaction> clazz;
    switch (type.toLowerCase()) {
      case "withdraw":
        clazz = WithDraw.class;
        break;
      case "deposit":
        clazz = Deposit.class;
        break;
      case "transfer":
        clazz = Transfer.class;
        break;
      default:
        return ResponseEntity.badRequest().body(List.of());
    }
    return ResponseEntity.ok(transactionService.listTransactionsByType(clazz));
  }

  // List all transactions for a wallet
  @GetMapping("/wallet/{walletId}")
  public ResponseEntity<List<Transaction>> getTransactionsByWalletId(@PathVariable Long walletId) {
    return ResponseEntity.ok(transactionService.listTransactionsByWalletId(walletId));
  }

  // List all failed transactions
  @GetMapping("/status/failed")
  public ResponseEntity<List<Transaction>> getFailedTransactions() {
    return ResponseEntity.ok(transactionService.listFailedTransactions());
  }

  // List all successful transactions
  @GetMapping("/status/success")
  public ResponseEntity<List<Transaction>> getSuccessfulTransactions() {
    return ResponseEntity.ok(transactionService.listSuccessfulTransactions());
  }

  // List all pending transactions
  @GetMapping("/status/pending")
  public ResponseEntity<List<Transaction>> getPendingTransactions() {
    return ResponseEntity.ok(transactionService.listPendingTransactions());
  }
}
