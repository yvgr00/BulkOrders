package com.orderprocessing.orders.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Table(name="orders")
public class Order{


	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(columnDefinition = "character varying (12) not null",length=12)
	private String orderId;

	@ManyToOne(optional=false)
	@JoinColumn(name="customer_id")
	private Customer customer;

	@Column(name="order_status")
	private String orderStatus;

	@Column(name="order_sub_total")
	private Double orderSubTotal;

	@Column(name="order_total_amount")
	private Double orderTotalAmount;

	@Column(name="order_tax")
	private Double orderTax;

	@Column(name="order_shipping_method")
	private String orderShippingMethod;

	@OneToMany( 
			fetch = FetchType.LAZY, 
			cascade=CascadeType.ALL,
			mappedBy="order"
			)
	private List<Transaction> transactions = new ArrayList<>();

	@OneToMany( 
			fetch = FetchType.LAZY, 
			cascade=CascadeType.ALL,
			mappedBy="order"
			)
	private List<OrderLineItems> orderLineItems = new ArrayList<>();

	@Column(name="shipping_address_line1")
	private String shippingAddressLine1;

	@Column(name="shipping_address_line2")
	private String shippingAddressLine2;

	@Column(name="shipping_city")
	private String shippingCity;

	@Column(name="shipping_state")
	private String shippingState;

	@Column(name="shipping_zipcode")
	private String shipping_zipcode;


	@Basic(optional=false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	private Date createdOn;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	public Order(String orderStatus, Double orderSubTotal, Double orderTotalAmount, Double orderTax,
			String orderShippingMethod, String shippingAddressLine1, String shippingAddressLine2, String shippingCity,
			String shippingState, String shipping_zipcode, Date createdOn, Date updatedOn) {
		this.orderStatus = orderStatus;
		this.orderSubTotal = orderSubTotal;
		this.orderTotalAmount = orderTotalAmount;
		this.orderTax = orderTax;
		this.orderShippingMethod = orderShippingMethod;
		this.shippingAddressLine1 = shippingAddressLine1;
		this.shippingAddressLine2 = shippingAddressLine2;
		this.shippingCity = shippingCity;
		this.shippingState = shippingState;
		this.shipping_zipcode = shipping_zipcode;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	public void addOrderLineItems(OrderLineItems orderLineItems) {
		this.orderLineItems.add(orderLineItems);
	}

	public void addTransactions(Transaction transaction) {
		this.transactions.add(transaction);
	}
}
