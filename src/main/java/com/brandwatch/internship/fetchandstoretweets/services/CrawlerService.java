package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import com.brandwatch.internship.fetchandstoretweets.services.utility.TweetsToMentions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrawlerService {

    private final Twitter twitter;
    private final MentionsRepository mentionsRepository;
    private final QueryRepository queryRepository;

    public CrawlerService(Twitter twitter, MentionsRepository mentionsRepository, QueryRepository queryRepository) {
        this.twitter = twitter;
        this.mentionsRepository = mentionsRepository;
        this.queryRepository = queryRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void crawl() {
        List<Query> queries = queryRepository.findAll();

        for (Query query : queries) {
            List<Tweet> tweets = twitter.searchOperations().search(query.getSearchString()).getTweets();
            List<Mention> mentions = TweetsToMentions.tweetsToMentions(tweets, query.getId());

            mentionsRepository.saveAll(mentions);
        }

    }

}
