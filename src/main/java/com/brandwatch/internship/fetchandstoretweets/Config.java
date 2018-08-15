package com.brandwatch.internship.fetchandstoretweets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@EnableScheduling
@Configuration
public class Config {

    @Bean
    public Twitter twitter(final @Value("${spring.social.twitter.appId}") String appId,
                           final @Value("${spring.social.twitter.appSecret}") String appSecret) {

        return new TwitterTemplate(appId, appSecret);
    }

}