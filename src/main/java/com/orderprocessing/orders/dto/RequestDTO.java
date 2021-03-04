package com.orderprocessing.orders.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

	@NotNull @NotEmpty
	private Customer customer;
    
	@NotNull @NotEmpty
	private String shippingMethod;
    
	@NotNull @NotEmpty
	private Double orderSubTotal;
	
	@NotNull @NotEmpty
	private Double tax;
	
	@NotNull @NotEmpty
	private Double orderTotalAmount;

	@NotNull @NotEmpty
	private String shipping_address_line1;
	
	@NotNull @NotEmpty
	private String shipping_address_line2;
	
	@NotNull @NotEmpty
	private String shipping_city;
	
	@NotNull @NotEmpty
	private String shipping_state;
	
	@NotNull @NotEmpty
	private String shipping_zipcode;

	@NotNull @NotEmpty
	private List<OrderLineItems> orderLineItems;

	@NotNull @NotEmpty
	private List<Double> paymentAmount;

	@NotNull @NotEmpty
	private List<PaymentMethod> paymentMethod;

}
