package com.orderprocessing.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderprocessing.orders.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
