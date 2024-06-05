package com.kafka.beginner.kafkabasic.topic;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

public class ProducerDemoWithCallback {

	private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class);

	public static void main(String[] args) {
		try {
			log.info("set producer topic connection with callback");
			Properties properties = new Properties();
			// connect to kafka localhost
			properties.setProperty("bootstrap.servers", "localhost:9092");

			// set producer property
			properties.setProperty("key.serializer", StringSerializer.class.getName());
			properties.setProperty("value.serializer", StringSerializer.class.getName());
			//properties.setProperty(ProducerConfig.GROUP_ID_CONFIG, value)

			// create the producer
			KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

			// create a producer record
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("sourabh_topic",
					"hello starting with kafka basic");

			producer.send(producerRecord);
			// send the data
			producer.send(producerRecord, new Callback() {

				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if (exception == null) {
						log.info("Received new Metdata /n" + "Topic {}", metadata.topic() + "/n" + "partition {}",
								metadata.partition() + "/n" + "Offset {}", metadata.offset() + "/n" + "Timestamp {}",
								metadata.timestamp());
					} else {
						log.error("Error while producing {}", exception);
					}

				}
			});

			// tell the producer to send all the data and block until done -synchronous
			producer.flush();

			// flush and close the producer
			producer.close();
			log.info("producer connection closed");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
