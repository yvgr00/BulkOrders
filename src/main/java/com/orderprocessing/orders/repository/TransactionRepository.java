package com.orderprocessing.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	List<Transaction> findByOrder(Order order);

}
