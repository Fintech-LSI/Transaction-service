package com.lsi.transaction.controller;

import com.lsi.transaction.entity.Transaction;
import com.lsi.transaction.entity.WithDraw;
import com.lsi.transaction.repository.TransactionRepository;
import com.lsi.transaction.repository.WithDrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class transactionController {
  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private WithDrawRepository withDrawRepository;

  @GetMapping("/test")
  public ResponseEntity<List<WithDraw>> test() {

    return ResponseEntity.ok(withDrawRepository.findAll());
  }



}
