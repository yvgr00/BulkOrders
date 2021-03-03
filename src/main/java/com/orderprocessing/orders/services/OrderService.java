package com.orderprocessing.orders.services;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.RequestUpdateDTO;
import com.orderprocessing.orders.dto.ResponseDTO;
import com.orderprocessing.orders.entities.Order;

public interface OrderService {

	public Order findOrderById(String theId);

	public ResponseDTO saveOrder(RequestDTO order);

	public String deleteById(String theId);
	
	public String updateOrders(RequestUpdateDTO updateOrders);

}
