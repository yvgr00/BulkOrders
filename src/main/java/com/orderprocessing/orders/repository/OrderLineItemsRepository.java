package com.orderprocessing.orders.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.entities.OrderLineItems;

public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {

	List<OrderLineItems> findByOrder(Order order);

}
