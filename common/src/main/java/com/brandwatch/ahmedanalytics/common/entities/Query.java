package com.brandwatch.ahmedanalytics.common.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Objects;

public class Query {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such Query")  // 404
    public static class NoSuchQueryException extends RuntimeException {
        public long id;

        public NoSuchQueryException(long id) {
            this.id = id;
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="searchString can not be empty")  // 400
    public static class EmptyQuerySearchStringException extends RuntimeException { }


    private final long id;
    private String searchString;

    public Query(
            @JsonProperty("id") long id,
            @JsonProperty("searchString") String searchString) {

        this.id = id;
        this.searchString = searchString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Query query = (Query) o;
        return id == query.id &&
                Objects.equals(searchString, query.searchString);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, searchString);
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

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
}
