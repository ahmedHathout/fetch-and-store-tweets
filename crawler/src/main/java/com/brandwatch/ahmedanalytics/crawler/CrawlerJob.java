package com.brandwatch.ahmedanalytics.crawler;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.entities.Query;
import com.brandwatch.ahmedanalytics.common.services.QueryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CrawlerJob {
    private static final ObjectMapper mapper = new ObjectMapper();

    private final Twitter twitter;
    private final Producer<String, String> producer;

    private QueryService queryService;

    public CrawlerJob(Twitter twitter, Producer<String, String> producer, QueryService queryService) {
        this.twitter = twitter;
        this.producer = producer;
        this.queryService = queryService;
    }

    @Scheduled(fixedRate = 60000)
    public void crawl() {
        List<Query> queries = queryService.findAll();
        for (Query query : queries) {
            List<Tweet> tweets = twitter.searchOperations().search(query.getSearchString()).getTweets();
            List<Mention> mentions = tweetsToMentions(tweets, query.getId());

            mentions.forEach(mention -> {
                try {
                    producer.send(new ProducerRecord<>("mention",
                            mapper.writeValueAsString(mention)));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static Mention tweetToMention(Tweet tweet, long queryId){
        return new Mention(new Mention.MentionId(tweet.getId(), queryId), tweet.getText(), tweet.getCreatedAt(), tweet.getFromUser(),
                tweet.getProfileImageUrl(), tweet.getLanguageCode(),
                tweet.getSource());
    }

    private static List<Mention> tweetsToMentions(List<Tweet> tweets, long queryId) {
        return tweets.stream()
                .map(tweet -> tweetToMention(tweet, queryId))
                .collect(Collectors.toList());
    }

}
