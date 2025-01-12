package com.lsi.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Long walletId; // ID of the wallet initiating or receiving the transaction

  @Column(nullable = false)
  private Double amount; // The transaction amount

  @Column(nullable = false)
  private LocalDateTime timestamp = LocalDateTime.now(); // When the transaction occurred

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionStatus status = TransactionStatus.PENDING; // PENDING, SUCCESS, FAILED

  @Column(nullable = true)
  private String description; // Optional description or reason for the transaction


}
