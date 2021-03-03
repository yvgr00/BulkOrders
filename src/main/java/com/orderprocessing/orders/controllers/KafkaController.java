package com.orderprocessing.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.orderprocessing.orders.dto.RequestDTO;
import com.orderprocessing.orders.producers.Producer;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
	
	 private final Producer producer;

	    @Autowired
	    KafkaController(Producer producer) {
	        this.producer = producer;
	    }

	    @PostMapping(value = "/publish")
	    public void sendMessageToKafkaTopic(@RequestBody RequestDTO message) {
	        this.producer.sendMessage(message);
	    }

}
