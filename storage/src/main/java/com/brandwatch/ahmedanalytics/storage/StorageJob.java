package com.brandwatch.ahmedanalytics.storage;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.repositories.MentionsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class StorageJob extends Thread{

    private static final ObjectMapper mapper = new ObjectMapper();

    private final AtomicBoolean closed = new AtomicBoolean(false);
    private final KafkaConsumer<String, String> mentionConsumer;

    @Autowired
    private MentionsRepository mentionsRepository;

    public StorageJob(KafkaConsumer<String, String> mentionConsumer) {
        this.mentionConsumer = mentionConsumer;
        this.mentionConsumer.subscribe(Collections.singletonList("mention"));
    }

    @Override
    public void run() {

        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                ConsumerRecords<String, String> records = mentionConsumer.poll(1000);

                try {
                    for (ConsumerRecord<String, String> record : records) {
                        mentionsRepository.save(mapper.readValue(record.value(), Mention.class));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (WakeupException e) {
            if (!closed.get()) {
                throw e;
            }
        } finally {
            mentionConsumer.close();
        }
    }

    public void shutdown() {
        closed.set(true);
        mentionConsumer.wakeup();
    }
}
