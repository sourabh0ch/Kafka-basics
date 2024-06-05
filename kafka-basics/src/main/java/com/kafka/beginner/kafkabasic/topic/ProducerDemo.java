package com.kafka.beginner.kafkabasic.topic;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {

	private static final Logger log = LoggerFactory.getLogger(ProducerDemo.class);

	public static void main(String[] args) {
		System.out.println("producer demo");
		
		try {
			System.out.println("set producer topic connection");
		Properties properties = new Properties();
		
		properties.put("bootstrap.servers", "localhost:9092");
		properties.put("acks", "all");
		properties.put("retries", 0);
		properties.put("batch.size", 16384);
		properties.put("linger.ms", 1);
		properties.put("buffer.memory", 33554432);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		// set producer property
		//properties.setProperty("key.serializer", StringSerializer.class.getName());
		//properties.setProperty("value.serializer", StringSerializer.class.getName());
		
		//create the producer
		//KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 5; i++) {
            producer.send(new ProducerRecord<>("sourabh_third_topic", Integer.toString(i), Integer.toString(i)));
        }

        producer.close();
		
//		//create a producer record
//		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("sourabh_topic", "hello starting with kafka basic");
//		
//		//sendt the data
//		producer.send(producerRecord);
//		
//		//tell the producer to send all the data and block until done -synchronous
//		producer.flush();
//		
//		//flush and close the producer
//		producer.close();
		System.out.println("producer connection closed");
		
		Properties consumerProperties = new Properties();
		consumerProperties.setProperty("bootstrap.servers", "localhost:9092");

		// set producer property
		consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-first-application");
		//consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(consumerProperties);
		consumer.subscribe(Arrays.asList("sourabh_third_topic"));
		
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
		for (ConsumerRecord<String, String> record : records) {
		    log.info(record.value());
		}
		consumer.close();
		System.out.println("consumer connection closed");
		}catch (Exception e) {
			System.out.println("failed with exception ...{}" + e.getMessage());
		}
		

	}
}
