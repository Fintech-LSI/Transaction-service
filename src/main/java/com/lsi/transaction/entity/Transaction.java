package com.lsi.transaction.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder
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

  @PrePersist
  public void prePersist() {
    if (timestamp == null) {
      timestamp = LocalDateTime.now();
    }
    if (status == null) {
      status = TransactionStatus.PENDING;
    }
  }
}
