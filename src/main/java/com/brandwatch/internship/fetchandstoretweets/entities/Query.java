package com.brandwatch.internship.fetchandstoretweets.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.annotation.Id;

public class Query {

    private final long id;
    private String searchString;

    public Query(long id, String searchString) {
        this.id = id;
        this.searchString = searchString;
    }

    public long getId() {
        return id;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
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
}
