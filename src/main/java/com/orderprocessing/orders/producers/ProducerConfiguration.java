package com.orderprocessing.orders.producers;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.orderprocessing.orders.dto.RequestDTO;

@Configuration
public class ProducerConfiguration {
	
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
	
	@Bean
	public ProducerFactory<String, RequestDTO> producerFactory(){
	    Map<String, Object> config = new HashMap<>();

	    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	    config.put(ProducerConfig.ACKS_CONFIG, "all");
	    config.put(ProducerConfig.RETRIES_CONFIG, 0);
	    config.put(ProducerConfig.BATCH_SIZE_CONFIG, 4);
	    config.put(ProducerConfig.LINGER_MS_CONFIG, 1);
	    config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
	    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

	    return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean
	public KafkaTemplate<String, RequestDTO> kafkaTemplate(){
	    return new KafkaTemplate<>(producerFactory());
	}

}
