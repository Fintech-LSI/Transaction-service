package com.lsi.transaction.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Deposit extends Transaction implements Serializable {

  @Column(nullable = false)
  private MoneyMethods method; // Deposit method, e.g., Cash, Bank Transfer

}
