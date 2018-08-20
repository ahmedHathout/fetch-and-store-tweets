package com.brandwatch.internship.fetchandstoretweets.controllers;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.services.QueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/queries")
public class QueryController {

    private QueryService queryService;

    private QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public List<Query> getAllQueries() {
        return this.queryService.getAllQueries();
    }

    @GetMapping(value = "/{id}")
    public Query getQuerById(@PathVariable("id") long id) {
        return queryService.getQueryById(id);
    }

    @PostMapping
    public Query createQuery(@RequestParam String searchString) {
        return queryService.createQuery(searchString);
    }

    @PatchMapping(value = "{id}")
    public Query updateQuery(@PathVariable long id, @RequestParam String searchString) {
        return queryService.updateQuery(id, searchString);
    }

}
