package com.orderprocessing.orders.producers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orderprocessing.orders.dto.RequestDTO;





@Service
public class Producer {


	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	private static final String TOPIC = "test";

	@Autowired
	private KafkaTemplate<String,RequestDTO> kafkaTemplate;


	public void sendMessage(RequestDTO order) {

		logger.info("ffff   "+TOPIC+"    "+order.getShipping_address_line1());
		logger.info("ddddd    "+this.kafkaTemplate);
		this.kafkaTemplate.send(TOPIC,order);


	}

}
