package com.lsi.transaction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest implements Serializable {
  private String transactionType;
  private Long walletId;
  private Long targetWalletId;
  private Double amount;
  private String description;
  private String moneyMethod;
}
