package com.pereira.kafka.commons.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Getter
@Slf4j
public class Consumer<K, V> extends Thread {
	private KafkaConsumer<K, V> consumer;
	private String[] topic;
	private String groupId;
	private boolean shutdown;
	private Processor<K, V> processor;
	public int pollingTime;
	public int sleepTime;

	{
		pollingTime = 0;
		sleepTime = 1000;
		shutdown = false;
	}

	public Consumer(Properties properties, String[] topics, Processor<K, V> processor) {
		this.groupId = properties.get(ConsumerConfig.GROUP_ID_CONFIG).toString();
		this.topic = topics;
		this.consumer = new KafkaConsumer<>(properties);
		this.processor = processor;
	}

	@Override
	public void run() {
		// subscribe topics
		log.info("Subscribing to topics: " +  topic);
		consumer.subscribe(Arrays.asList(topic));

		// poll and process records
		try {
			keepPolling();
		} catch (InterruptedException e) {
			log.error("THREAD INTERRUPTED.", e);
		}

		// when the shutdown is called, close the consumer connection.
		log.info("Consumer {} is being closed down.", this.getThreadGroup());
		consumer.close();
	}

	private void keepPolling() throws InterruptedException {
		// keep on polling until shutdown for this thread is called.
		final Duration duration = Duration.ofSeconds(pollingTime);
		while (!shutdown) {
			ConsumerRecords<K, V> records = consumer.poll(duration);

			// if polling gave no tasks, then sleep this thread for n seconds.
			if (records.isEmpty()) {
				log.debug("NO RECORDS fetched from queue. Putting current THREAD to SLEEP.");
				Thread.sleep(sleepTime);
				continue;
			}

			log.info("Processing a batch of records.");
			if (!processor.process(records)) {
				log.error("ERROR occurred while PROCESSING RECORDS.");
			}
		}
	}

	public static abstract class Processor<K, V> {
		protected abstract Boolean process(ConsumerRecords<K, V> record);
	}

	public void close() {
		shutdown = true;
	}
}