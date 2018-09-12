package com.brandwatch.ahmedanalytics.crawler;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Properties;

@Configuration
@EnableScheduling
public class Config {

    @Bean
    public Twitter twitter(final @Value("${spring.social.twitter.appId}") String appId,
                           final @Value("${spring.social.twitter.appSecret}") String appSecret) {

        return new TwitterTemplate(appId, appSecret);
    }

    @Bean
    public KafkaProducer<String, Mention> kafkaProducer(@Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new KafkaProducer<>(props);
    }

}
