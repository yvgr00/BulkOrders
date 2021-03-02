package com.orderprocessing.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderprocessing.orders.entities.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
