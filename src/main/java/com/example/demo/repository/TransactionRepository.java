package com.example.demo.repository;

import com.example.demo.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @EntityGraph(attributePaths = {"fromAccount", "toAccount"})
    @Query("""
            select t from Transaction t
            where (t.fromAccount.accountNumber = :accountNumber or t.toAccount.accountNumber = :accountNumber)
              and (:fromDate is null or t.transactionDate >= :fromDate)
              and (:toDate is null or t.transactionDate <= :toDate)
            """)
    Page<Transaction> findHistory(String accountNumber, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
