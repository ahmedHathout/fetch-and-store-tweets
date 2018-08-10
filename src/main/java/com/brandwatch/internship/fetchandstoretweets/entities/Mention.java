package com.brandwatch.internship.fetchandstoretweets.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Mention {

    private static final long serialVersionUID = 1L;
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

    public Mention(long id, String idStr, String text, Date createdAt, String fromUser, String profileImageUrl,
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
