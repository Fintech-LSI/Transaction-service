package com.lsi.transaction.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@SuperBuilder
public class WithDraw extends Transaction implements Serializable {
}
