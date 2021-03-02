package com.orderprocessing.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderprocessing.orders.entities.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

}
