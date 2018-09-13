package com.brandwatch.ahmedanalytics.crawler;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.entities.Query;
import com.brandwatch.ahmedanalytics.common.services.QueryService;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CrawlerJob {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerJob.class);

    private static final String TOPIC_NAME = "mention";

    private final Twitter twitter;
    private final Producer<String, Mention> producer;

    private QueryService queryService;

    public CrawlerJob(Twitter twitter, Producer<String, Mention> producer, QueryService queryService) {
        this.twitter = twitter;
        this.producer = producer;
        this.queryService = queryService;
        logger.info("Crawler Created");
    }

    @Scheduled(fixedRate = 60000)
    public void crawl() {
        logger.info("Crawling...");
        long startTime = System.currentTimeMillis();

        List<Query> queries = queryService.findAll();
        for (Query query : queries) {
            List<Tweet> tweets = twitter.searchOperations().search(query.getSearchString()).getTweets();
            List<Mention> mentions = tweetsToMentions(tweets, query.getId());

            logger.debug("Found {} mentions for query {}", mentions.size(), query.getId());

            mentions.forEach(mention -> producer.send(new ProducerRecord<>(TOPIC_NAME, mention)));
        }
        logger.info("Finished crawling in {} ms", System.currentTimeMillis() - startTime);
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
