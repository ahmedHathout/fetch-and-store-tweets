package com.brandwatch.internship.fetchandstoretweets.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class Query {

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Query")  // 404
    public static class NoSuchQueryException extends RuntimeException {
        public long id;

        public NoSuchQueryException(long id) {
            super();
            this.id = id;
        }
    }

    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="searchString can not be empty")  // 400
    public static class EmptyQuerySearchStringException extends RuntimeException { }


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
