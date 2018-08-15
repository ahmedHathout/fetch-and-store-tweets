package com.brandwatch.internship.fetchandstoretweets.services.utility;

import com.brandwatch.internship.fetchandstoretweets.entities.Mention;
import org.springframework.social.twitter.api.Tweet;

import java.util.List;
import java.util.stream.Collectors;

public class TweetsToMentions {

    private TweetsToMentions() {
        throw new AssertionError();
    }


    private static Mention tweetToMention(Tweet tweet, long queryId){
        return new Mention(new Mention.MentionId(tweet.getId(), queryId), tweet.getText(), tweet.getCreatedAt(), tweet.getFromUser(),
                tweet.getProfileImageUrl(), tweet.getToUserId(), tweet.getFromUserId(), tweet.getLanguageCode(),
                tweet.getSource());
    }

    public static List<Mention> tweetsToMentions(List<Tweet> tweets, long queryId) {
        return tweets.stream()
                .map(tweet -> tweetToMention(tweet, queryId))
                .collect(Collectors.toList());
    }

}
