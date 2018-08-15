package com.brandwatch.internship.fetchandstoretweets.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;
import org.springframework.social.twitter.api.Tweet;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Mention {

    public static class MentionId {
        private long tweetId;
        private long queryId;

        public MentionId(long tweetId, long queryId) {
            this.tweetId = tweetId;
            this.queryId = queryId;
        }

        public long getTweetId() {
            return tweetId;
        }

        public long getQueryId() {
            return queryId;
        }
    }

    @Id
    private final MentionId id;
    private final String text;
    private final Date createdAt;
    private String fromUser;
    private String profileImageUrl;
    private Long toUserId;
    private long fromUserId;
    private String languageCode;
    private String source;

    public Mention(MentionId id, String text, Date createdAt, String fromUser, String profileImageUrl,
                    Long toUserId, long fromUserId, String languageCode, String source) {

        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.fromUser = fromUser;
        this.profileImageUrl = profileImageUrl;
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
        this.languageCode = languageCode;
        this.source = source;

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

    public MentionId getId() {
        return id;
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

}