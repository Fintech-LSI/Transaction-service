package com.lsi.transaction.repository;

import com.lsi.transaction.entity.Transaction;
import com.lsi.transaction.entity.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  // Find transactions by wallet ID
  List<Transaction> findByWalletId(Long walletId);

  // Find transactions by type
  @Query("SELECT t FROM Transaction t WHERE TYPE(t) = :type")
  List<Transaction> findByType(Class<? extends Transaction> type);

  // Find transactions by status
  List<Transaction> findByStatus(TransactionStatus status);
}
