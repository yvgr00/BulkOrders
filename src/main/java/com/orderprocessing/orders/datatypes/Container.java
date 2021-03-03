package com.orderprocessing.orders.datatypes;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Container {

	private String id;
    private List<Integer> data;
    
    
	


	public Container(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Container [id=" + id + ", data=" + data + "]";
	}
    
    
}
