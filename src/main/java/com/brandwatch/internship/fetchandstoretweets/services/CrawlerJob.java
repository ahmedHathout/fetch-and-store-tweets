package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CrawlerJob {

    private final Twitter twitter;
    private MentionService mentionService;
    private QueryService queryService;

    public CrawlerJob(Twitter twitter, MentionService mentionService, QueryService queryService) {
        this.twitter = twitter;
        this.mentionService = mentionService;
        this.queryService = queryService;
    }

    @Scheduled(fixedRate = 60000)
    public void crawl() {
        List<Query> queries = queryService.findAll();

        for (Query query : queries) {
            List<Tweet> tweets = twitter.searchOperations().search(query.getSearchString()).getTweets();
            mentionService.saveTweetsForQuery(tweets, query.getId());
        }

    }

}
