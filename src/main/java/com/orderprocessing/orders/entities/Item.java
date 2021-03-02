package com.orderprocessing.orders.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name="item")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id")
	private Long itemId;

	@Column(name="item_price")
	private Double itemPrice;

	@Column(name="item_name")
	private String itemName;

	@OneToMany( 
			fetch = FetchType.LAZY, 
			cascade=CascadeType.ALL,
			mappedBy="item"
			)
	@JsonProperty(access=Access.WRITE_ONLY)
	private List<OrderLineItems> orderLineItems = new ArrayList<>();

	public Item(Double itemPrice, String itemName) {
		this.itemPrice = itemPrice;
		this.itemName = itemName;
	}
}
