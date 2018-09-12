package com.brandwatch.ahmedanalytics.storage;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.repositories.MentionsRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class StorageConsumerRunnable implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(StorageConsumerRunnable.class);
    private static final String TOPIC_NAME = "mention";

    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer<String, Mention> mentionConsumer;

    @Autowired
    private MentionsRepository mentionsRepository;

    public StorageConsumerRunnable(KafkaConsumer<String, Mention> mentionConsumer) {
        this.mentionConsumer = mentionConsumer;
        logger.info("StorageConsumerRunnable Created.");
    }

    @Override
    public void run() {
        this.mentionConsumer.subscribe(Collections.singletonList(TOPIC_NAME));
        logger.info(String.format("Subscribed to %s", TOPIC_NAME));

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ConsumerRecords<String, Mention> records = mentionConsumer.poll(1000);

                for (ConsumerRecord<String, Mention> record : records) {
                    mentionsRepository.save(record.value());
                }

                if (!records.isEmpty()) {
                    logger.debug(String.format("%d records found and saved.", records.count()));
                }
            }
        } catch (WakeupException e) {
            if (!closed.get()) {
                throw e;
            }
        } finally {
            mentionConsumer.close();
            logger.info("mentionConsumer closed.");
        }
    }

    public void shutdown() {
        closed.set(true);
        mentionConsumer.wakeup();
        logger.info("shutdown() executed");
    }

    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(this);
        logger.info("New consumer thread started.");
    }
}
