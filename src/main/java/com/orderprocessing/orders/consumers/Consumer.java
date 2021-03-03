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
import com.orderprocessing.orders.services.OrderService;

@Service
public class Consumer {
	
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);
	
//	private OrderController ordCont;
	
	@Autowired
	private OrderService orderService;


    @KafkaListener(id = "batch", topics = "${spring.kafka.topic.app}"	)
    public void consume(@Payload RequestDTO messages) throws IOException {
    	
    	orderService.saveOrder(messages);
    	
        logger.info("Received message='{}' with partition-offset='{}'", messages.getShipping_address_line1());
        
        logger.info("All batch messages received");
       
    }
	
//	@KafkaListener(
//	        topicPartitions = { @TopicPartition(topic = "test", partitions = { "1" })},
//	        groupId = "batch",
//	        containerFactory = "kafkaListenerContainerFactory",
//	        autoStartup = "true")
//	public void consumeFromCoreTopicPartitionZERO(@Payload RequestDTO containers) throws IOException{
//	    logger.info("\n/******* Consume TEST-TOPIC Partition ---->>>>>>    ONE ********/\n"+containers.getShipping_address_line1());
//	    logger.info("\n/******* Consume TEST-TOPIC Partition ---->>>>>>    ONE ********/\n"+containers.getShipping_city());
//	}
	
	
//	@KafkaListener(id = "batch", topics = "${spring.kafka.topic.app}")
//    public void receive(@Payload RequestDTO messages) {
//
//        
//            logger.info("Received message='{}' with partition-offset='{}'", messages.getOrderSubTotal());
//                               
//       
//        logger.info("All batch messages received");
//    }

}
