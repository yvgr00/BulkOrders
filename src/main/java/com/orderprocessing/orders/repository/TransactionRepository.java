package com.orderprocessing.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.orders.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{


}
