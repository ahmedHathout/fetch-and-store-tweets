package com.brandwatch.internship.fetchandstoretweets.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.atomic.AtomicLong;

public class Query {

    private static AtomicLong counter = new AtomicLong();
    private final long id;
    private final String searchString;

    public Query(String searchString) {
        this.id = counter.incrementAndGet();
        this.searchString = searchString;
    }

    public long getId() {
        return id;
    }

    public String getSearchString() {
        return searchString;
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
