package com.brandwatch.internship.fetchandstoretweets.controllers;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionHandlerController {

    @ExceptionHandler(Query.NoSuchQueryException.class)
    public String noSuchQueryHandler(Query.NoSuchQueryException e) {
        return "No query with such ID: " + e.id;
    }

    @ExceptionHandler(Query.EmptyQuerySearchStringException.class)
    public String emptyQuerySearchStringHandler() {
        return "searchString can not be empty";
    }
}
