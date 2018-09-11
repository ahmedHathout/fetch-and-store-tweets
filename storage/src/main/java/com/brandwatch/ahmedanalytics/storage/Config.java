package com.brandwatch.ahmedanalytics.storage;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@Configuration
@EnableScheduling
public class Config {

    @Bean
    public KafkaConsumer<String, String> mentionConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "kafka:9092");
        props.put("group.id", "storage-consumer");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(props);
    }

    @Bean(initMethod = "run", destroyMethod = "shutdown")
    public ConsumerThread consumerThread() {
        return new ConsumerThread(mentionConsumer());
    }
}
