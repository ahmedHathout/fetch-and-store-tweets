package com.brandwatch.ahmedanalytics.crawler.job;

import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.entities.Query;
import com.brandwatch.ahmedanalytics.common.services.MentionService;
import com.brandwatch.ahmedanalytics.common.services.QueryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
            mentionService.saveMentions(tweetsToMentions(tweets, query.getId()));
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
