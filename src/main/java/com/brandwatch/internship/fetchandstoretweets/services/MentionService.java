package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import com.brandwatch.internship.fetchandstoretweets.repositories.MentionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MentionService {

    private MentionsRepository mentionsRepository;

    @Autowired
    private MentionService(MentionsRepository mentionsRepository) {
        this.mentionsRepository = mentionsRepository;
    }

    public List<Mention> getMentionsByQueryId(long queryId) {
        return mentionsRepository.findById_QueryId(queryId);
    }

    void saveTweetsForQuery(List<Tweet> tweets, long queryId) {
        mentionsRepository.saveAll(tweetsToMentions(tweets, queryId));
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
