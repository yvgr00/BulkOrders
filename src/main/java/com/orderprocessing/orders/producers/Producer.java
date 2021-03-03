package com.orderprocessing.orders.producers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.RequestUpdateDTO;





@Service
public class Producer {


	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "test";
	private static final String TOPIC1 = "updateOrders";

	@Autowired
	private KafkaTemplate<String,RequestDTO> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String,RequestUpdateDTO> kafkaUpdateTemplate;


	public void sendMessage(RequestDTO order) {

		logger.info("ffff   "+TOPIC+"    "+order.getShipping_address_line1());
		this.kafkaTemplate.send(TOPIC,order);


	}
	
	public void sendMessage(RequestUpdateDTO order) {

		logger.info("ffff   "+TOPIC1+"    "+order.getOrderId());
		this.kafkaUpdateTemplate.send(TOPIC1,order);


	}
	

}
