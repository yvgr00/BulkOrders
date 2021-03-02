package com.orderprocessing.orders.dto;

import java.util.List;

import com.orderprocessing.orders.entities.Customer;
import com.orderprocessing.orders.entities.OrderLineItems;
import com.orderprocessing.orders.entities.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

	private Customer customer;

	private String shippingMethod;

	private Double orderSubTotal;
	private Double tax;
	private Double orderTotalAmount;

	private String shipping_address_line1;
	private String shipping_address_line2;
	private String shipping_city;
	private String shipping_state;
	private String shipping_zipcode;

	private List<OrderLineItems> orderLineItems;

	private List<Double> paymentAmount;

	private List<PaymentMethod> paymentMethod;

}
