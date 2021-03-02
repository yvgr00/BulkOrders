package com.orderprocessing.orders.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private final Long transactionId = 0L;

	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Order order;

	@ManyToOne
	@JoinColumn(name="payment_method_id")
	private PaymentMethod paymentMethod;

	@Column(name="transaction_amount")
	private Double transactionAmount;

	@Column(name="transaction_status")
	private String transactionStatus;

}
