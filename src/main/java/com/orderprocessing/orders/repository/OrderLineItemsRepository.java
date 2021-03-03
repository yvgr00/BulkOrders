package com.orderprocessing.orders.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.orderprocessing.orders.entities.OrderLineItems;

public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {


}
