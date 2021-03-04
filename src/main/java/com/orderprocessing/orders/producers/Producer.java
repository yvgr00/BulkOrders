package com.orderprocessing.orders.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.dto.RequestUpdateDTO;


@Service
public class Producer {
	
	private static final Logger logger = LoggerFactory.getLogger(Producer.class);
	
	@Value("${spring.kafka.topic.create}")
	private String topic;
	
	@Value("${spring.kafka.topic.update}")
	private String topic1;

	@Autowired
	private KafkaTemplate<String,RequestDTO> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String,RequestUpdateDTO> kafkaUpdateTemplate;


	public void sendMessage(RequestDTO order) {
		this.kafkaTemplate.send(topic,order);
	}
	
	public void sendMessage(RequestUpdateDTO order) {
		this.kafkaUpdateTemplate.send(topic1,order);
	}
	

}
