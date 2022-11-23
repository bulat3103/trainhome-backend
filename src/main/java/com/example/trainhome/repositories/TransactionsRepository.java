package com.example.trainhome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transaction;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
}
