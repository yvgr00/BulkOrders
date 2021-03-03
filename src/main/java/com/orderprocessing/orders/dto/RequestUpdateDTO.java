package com.orderprocessing.orders.dto;

import java.util.List;

import com.orderprocessing.orders.entities.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDTO {
	
	
	private String orderId;
	
	private List<OrderLineItems> orderLineItems;

}
