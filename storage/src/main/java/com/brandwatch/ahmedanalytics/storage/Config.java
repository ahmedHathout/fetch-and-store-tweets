package com.brandwatch.ahmedanalytics.storage;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Properties;

@Configuration
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public KafkaConsumer<String, Mention> mentionConsumer(@Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "storage-consumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new KafkaConsumer<>(props, new StringDeserializer(), new JsonDeserializer<>(Mention.class, objectMapper()));
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public  StorageConsumerRunnable consumerThread(@Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers) {
        return new StorageConsumerRunnable(mentionConsumer(bootstrapServers));
    }
}
