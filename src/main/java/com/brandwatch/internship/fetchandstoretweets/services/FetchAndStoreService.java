package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class FetchAndStoreService {

    private Twitter twitter;

    private MentionsRepository mentionsRepository;
    private QueryRepository queryRepository;

    private int counter;
    private String[] searchStrings = {"#maven", "#spring", "#intellij_idea", "#kafka"};

    @Autowired
    public FetchAndStoreService(Twitter twitter, MentionsRepository mentionsRepository,
                                QueryRepository queryRepository) {

        this.twitter = twitter;
        this.mentionsRepository = mentionsRepository;
        this.queryRepository = queryRepository;

        this.mentionsRepository.deleteAll();
        this.queryRepository.deleteAll();

    }

    @Scheduled(fixedRate = 5000)
    public void run() {

        Query query = new Query(counter + 1, searchStrings[counter++ % 4]);

        SearchResults searchResults= twitter.searchOperations().search(query.getSearchString());

        List<Tweet> tweets = searchResults.getTweets();
        List<Mention> mentions = Mention.tweetsToMentions(tweets, query.getId());

        mentionsRepository.saveAll(mentions);
        queryRepository.save(query);

        // The next lines are for printing the mentions and queries.

        System.out.println("Here is a list of all the queries:-");
        List<Query> savedQueries = queryRepository.findAll();
        savedQueries.sort((q1, q2) -> (int)(q1.getId() - q2.getId()));
        savedQueries.forEach(System.out::println);


        System.out.println("Here is a list of all the mentions:-");
        mentionsRepository.findAll().forEach(System.out::println);

    }


}
