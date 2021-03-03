package com.orderprocessing.orders.dto;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestListDTO {
	
	private List<RequestUpdateDTO> requestUpdateDTO;

}
