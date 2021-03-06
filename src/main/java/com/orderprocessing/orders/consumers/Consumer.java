package com.orderprocessing.orders.consumers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.RequestUpdateDTO;
import com.orderprocessing.orders.services.OrderService;

@Service
public class Consumer {

	private final Logger logger = LoggerFactory.getLogger(Consumer.class);



	@Autowired
	private OrderService orderService;


	@KafkaListener(id = "batch", topics = "${spring.kafka.topic.create}")
	public void consume(@Payload RequestDTO messages) throws IOException {

		orderService.saveOrder(messages);

		logger.info("Received message='{}' with partition-offset='{}'", messages.getShipping_address_line1());

		logger.info("All batch messages received");

	}
	
	@KafkaListener(id = "updates", topics = "${spring.kafka.topic.update}", containerFactory = "kafkaListenerUpdateContainerFactory")
	public void consume(@Payload RequestUpdateDTO messages) throws IOException {
		
		logger.info("Received message='{}' with partition-offset='{}'", messages.getOrderId());

		logger.info("All batch messages received");

		orderService.updateOrders(messages);

		

	}

}
