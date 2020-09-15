package com.pereira.kafka.commons.producer;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class Producer<K, V> {

	KafkaProducer<K, V> producer;

	public Producer(final Properties properties) {
		producer = new KafkaProducer<K,V>(properties);
	}
	
	public Future<RecordMetadata> send(final String topic, K key, V value, Callback callback) {
		return producer.send(buildRecord(topic, key, value));
	}
	
	private ProducerRecord<K, V> buildRecord(String topic, K key, V value) {
		return new ProducerRecord<>(topic, key, value);
	}
	
	public void close() {
		producer.close();
	}
}
