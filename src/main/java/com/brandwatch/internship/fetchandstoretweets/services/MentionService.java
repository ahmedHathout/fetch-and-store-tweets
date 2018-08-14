package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import java.util.List;

@Service
@Singleton
public class MentionService {

    private Twitter twitter;
    private MentionsRepository mentionsRepository;
    private QueryRepository queryRepository;

    @Autowired
    private MentionService(Twitter twitter, MentionsRepository mentionsRepository, QueryRepository queryRepository) {
        this.twitter = twitter;
        this.mentionsRepository = mentionsRepository;
        this.queryRepository = queryRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void run() {
        List<Query> queries = queryRepository.findAll();

        for (Query query : queries) {
            List<Tweet> tweets = twitter.searchOperations().search(query.getSearchString()).getTweets();
            List<Mention> mentions = Mention.tweetsToMentions(tweets, query.getId());

            mentionsRepository.saveAll(mentions);
        }

        System.out.println(mentionsRepository.findAll().size());
    }

    public List<Mention> getMentionsByQueryId(long queryId) {
        return mentionsRepository.findById_QueryId(queryId);
    }

}
