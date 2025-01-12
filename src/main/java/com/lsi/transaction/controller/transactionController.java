package com.lsi.transaction.controller;

import com.lsi.transaction.controller.dto.request.CreateWalletRequest;
import com.lsi.transaction.controller.dto.response.WalletResponse;
import com.lsi.transaction.service.feignClientService.WalletServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class transactionController {
  @Autowired
  WalletServiceClient walletServiceClient;

  @GetMapping("/test")
  public ResponseEntity<WalletResponse> test() {
    CreateWalletRequest createWalletRequest = CreateWalletRequest.builder()
      .initialBalance(0.0)
      .currencyCode("MAD")
      .userId(1L)
      .build();

    return ResponseEntity.ok(walletServiceClient.createWallet(createWalletRequest));
  }



}
