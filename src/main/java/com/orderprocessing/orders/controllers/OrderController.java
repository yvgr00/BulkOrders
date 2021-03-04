package com.orderprocessing.orders.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderprocessing.orders.dto.RequestBulkDTO;
import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.RequestListDTO;
import com.orderprocessing.orders.dto.RequestUpdateDTO;
import com.orderprocessing.orders.dto.ResponseDTO;
import com.orderprocessing.orders.entities.Order;
import com.orderprocessing.orders.producers.Producer;
import com.orderprocessing.orders.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	private  static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private Producer producer;

	@GetMapping("/order/{id}")
	public ResponseEntity<ResponseDTO> getOrderById(@Valid @PathVariable("id") final String theId) {

		HttpStatus httpStatus = HttpStatus.OK;
		ResponseDTO responseDto = new ResponseDTO();

		if(theId==null) {
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto.setOrder(null);
			responseDto.setMessage("Please send a valid orderId");
			return new ResponseEntity<ResponseDTO>(responseDto,httpStatus);
		}
		Order response = orderService.findOrderById(theId);

		if(response==null) {

			httpStatus = HttpStatus.NOT_FOUND;
			responseDto.setOrder(response);
			responseDto.setMessage("Did not find the orderId - "+theId);
			return new ResponseEntity<ResponseDTO>(responseDto,httpStatus);
		}

		responseDto.setOrder(response);
		responseDto.setMessage("Found the orderID - "+theId);

		return new ResponseEntity<ResponseDTO>(responseDto,httpStatus);
	}



	@PostMapping(value = "/order", consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ResponseDTO> createOrder(@Valid @RequestBody RequestDTO theOrder) {
		logger.info("OrderController: createOrder() endpoint is called for request: " + theOrder.toString());

		HttpStatus httpStatus = HttpStatus.OK;
		ResponseDTO responseDto = new ResponseDTO();

		if(theOrder.equals(null) || theOrder.toString().equals("{}")) {
			httpStatus = HttpStatus.BAD_REQUEST;
			responseDto.setMessage("Please send valid order object");
			return new ResponseEntity<ResponseDTO>(responseDto, httpStatus);
		}

		Double totalAmnt = theOrder.getOrderTotalAmount();
		List<Double> paymentAmnts = theOrder.getPaymentAmount();
		Double paidAmntTotal = paymentAmnts.stream().mapToDouble(Double::doubleValue).sum();

		if(round(totalAmnt,2) != round(paidAmntTotal,2)) {
			httpStatus = HttpStatus.BAD_REQUEST;
			logger.info("Payments not ok. Need "+totalAmnt+". Received "+paidAmntTotal);
			responseDto.setMessage("Payments not ok. Need "+totalAmnt+". Received "+paidAmntTotal);
			return new ResponseEntity<ResponseDTO>(responseDto, httpStatus);
		}

		httpStatus = HttpStatus.OK;
		responseDto = orderService.saveOrder(theOrder);

		if(responseDto.getOrder() != null) {
			logger.info("Successfully generated order confirmation id");
		} else {
			logger.error("Order confirmation response is null for the request" + theOrder.toString());
			httpStatus = HttpStatus.BAD_REQUEST;
		}
		logger.info(responseDto.getMessage());
		return new ResponseEntity<ResponseDTO>(responseDto, httpStatus);
	}


	@DeleteMapping("/order/{id}")
	public ResponseEntity<String> cancelOrderById(@PathVariable("id") final String theId) {

		HttpStatus httpStatus = HttpStatus.OK;

		if(theId==null) {
			httpStatus = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<String>("Please send a valid orderId",httpStatus);
		}
		String response = orderService.deleteById(theId);

		if(response==null) {
			httpStatus = HttpStatus.NOT_FOUND;
			return new ResponseEntity<String>("orderId not found",httpStatus);
		}

		return new ResponseEntity<String>("canceled the order - "+theId,httpStatus);
	}


	@PostMapping(value = "/bulkorders", consumes = {MediaType.APPLICATION_JSON_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> sendBulkOrders(@Valid @RequestBody RequestBulkDTO theBulkOrder) {

		HttpStatus httpStatus = HttpStatus.OK;

		if(theBulkOrder.getOrders()==null) {
			httpStatus = HttpStatus.BAD_REQUEST;
			return new ResponseEntity<String>("Please send valid orders",httpStatus);
		}

		List<RequestDTO> orders = theBulkOrder.getOrders();


		for(int i=0;i<orders.size();i++) {


			RequestDTO order = orders.get(i);
			Double totalAmnt = order.getOrderTotalAmount();
			List<Double> paymentAmnts = order.getPaymentAmount();
			Double paidAmntTotal = paymentAmnts.stream().mapToDouble(Double::doubleValue).sum();

			if(round(totalAmnt,2) != round(paidAmntTotal,2)) {
				httpStatus = HttpStatus.BAD_REQUEST;
				logger.info("Payments not ok. Need "+totalAmnt+". Received "+paidAmntTotal);
				return new ResponseEntity<String>("payment not ok",httpStatus);
			}
			producer.sendMessage(order);
		}
		return new ResponseEntity<String>("success",httpStatus);
	}

	@PutMapping(value = "/updateorders")
	public ResponseEntity<String> updateBulkOrders(@Valid @RequestBody RequestListDTO theBulkOrder) {
		HttpStatus httpStatus = HttpStatus.OK;

		if(theBulkOrder.getRequestUpdateDTO()==null) {
			httpStatus = HttpStatus.BAD_REQUEST;
			new ResponseEntity<String>("Please send valid orders",httpStatus);
		}

		List<RequestUpdateDTO> requestDTO = theBulkOrder.getRequestUpdateDTO();

		for(int i=0;i<requestDTO.size();i++) {
			RequestUpdateDTO order = requestDTO.get(i);
			producer.sendMessage(order);
		}
		return new ResponseEntity<String>("success",httpStatus);
	}



	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
