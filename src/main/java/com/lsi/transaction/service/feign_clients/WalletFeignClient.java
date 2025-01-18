package com.lsi.transaction.service.feign_clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "WALLET-SERVICE")
public interface WalletFeignClient {
  @GetMapping("/api/wallets/{walletId}/user")
  public Long getUser(@PathVariable("walletId") Long walletId);
}
