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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="order_line_items")
public class OrderLineItems {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="order_item_id")
	private Long orderItemId;

	@Column(name="quantity")
	private Long quantity;

	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonProperty(access=Access.WRITE_ONLY)
	private Order order;

	@ManyToOne
	@JoinColumn(name="item_id")
	private Item item;

	public OrderLineItems(Long quantity) {
		this.quantity = quantity;
	}

	public OrderLineItems(Long quantity, Order order, Item item) {
		this.quantity = quantity;
		this.order = order;
		this.item = item;
	}

}
