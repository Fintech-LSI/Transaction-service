package com.lsi.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends Transaction implements Serializable {
  @Column(nullable = false)
  private Long targetWalletId; // The wallet receiving the funds
}
