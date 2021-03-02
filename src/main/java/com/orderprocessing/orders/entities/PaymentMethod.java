package com.orderprocessing.orders.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payment_method")
public class PaymentMethod {

	@Id
	@Column(name="payment_method_id")
	private Long paymentMethodId;

	@Column(name="payment_method")
	private String paymentMethod;

	@Column(name="billing_address_line1")
	private String billingAddressLine1;

	@Column(name="billing_address_line2")
	private String billingAddressLine2;

	@Column(name="billing_city")
	private String billingCity;

	@Column(name="billing_state")
	private String billingState;

	@Column(name="billing_zipcode")
	private String billingZipcode;

	@OneToMany( 
			fetch = FetchType.LAZY, 
			cascade=CascadeType.ALL,
			mappedBy="paymentMethod"
			)
	@JsonProperty(access=Access.WRITE_ONLY)
	private List<Transaction> transactions;

}
