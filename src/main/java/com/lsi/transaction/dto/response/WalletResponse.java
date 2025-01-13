package com.lsi.transaction.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse implements Serializable {
  private Long id;
  private Long userId;
  private Double balance;
  private String currencyCode;
  private String currencyName;
  private String userName; // Add userName
}
