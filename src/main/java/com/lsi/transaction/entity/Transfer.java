package com.lsi.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.io.Serializable;

@Entity
public class Transfer extends Transaction implements Serializable {
  @Column(nullable = false)
  private Long targetWalletId; // The wallet receiving the funds
}
