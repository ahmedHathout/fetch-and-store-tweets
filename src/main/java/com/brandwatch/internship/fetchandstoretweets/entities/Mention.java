package com.brandwatch.internship.fetchandstoretweets.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.twitter.api.Tweet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mention {

    private final long id;
    private final String idStr;
    private final String text;
    private final Date createdAt;
    private String fromUser;
    private String profileImageUrl;
    private Long toUserId;
    private long fromUserId;
    private String languageCode;
    private String source;
    private long queryId;

    private Mention(long id, String idStr, String text, Date createdAt, String fromUser, String profileImageUrl,
                   Long toUserId, long fromUserId, String languageCode, String source, long queryId) {

        this.id = id;
        this.idStr = idStr;
        this.text = text;
        this.createdAt = createdAt;
        this.fromUser = fromUser;
        this.profileImageUrl = profileImageUrl;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.languageCode = languageCode;
        this.source = source;
        this.queryId = queryId;
    }

    private static Mention tweetToMention(Tweet tweet, long queryId){
        return new Mention(tweet.getId(), tweet.getIdStr(),tweet.getText(), tweet.getCreatedAt(), tweet.getFromUser(),
                tweet.getProfileImageUrl(), tweet.getToUserId(), tweet.getFromUserId(), tweet.getLanguageCode(),
                tweet.getSource(), queryId);
    }

    public static List<Mention> tweetsToMentions(List<Tweet> tweets, long queryId) {
        List<Mention> mentions = new ArrayList<>();

        tweets.forEach(tweet -> mentions.add(tweetToMention(tweet, queryId)));

        return mentions;
    }


    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public long getId() {
        return id;
    }

    public String getIdStr() {
        return idStr;
    }

    public String getText() {
        return text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getSource() {
        return source;
    }

    public long getQueryId() {
        return queryId;
    }
}
