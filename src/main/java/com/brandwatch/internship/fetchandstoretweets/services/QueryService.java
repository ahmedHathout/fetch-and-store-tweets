package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class QueryService {

    private AtomicLong topId;
    private QueryRepository queryRepository;

    @Autowired
    private QueryService(QueryRepository queryRepository) {
        try {
            this.topId = new AtomicLong(queryRepository.findFirstByOrderByIdDesc().getId());
        } catch (NullPointerException e) {
            this.topId = new AtomicLong();
        }

        this.queryRepository = queryRepository;
    }

    public List<Query> getAllQueries() {
        List<Query> queries = queryRepository.findAll();
        queries.sort(Comparator.comparing(Query::getId));

        return queries;
    }

    public Query createQuery(String searchString) {
        if (searchString.equals("")) {
            throw new Query.EmptyQuerySearchStringException();
        }

        return queryRepository.save(new Query(topId.incrementAndGet(), searchString));
    }

    public Query getQueryById(long id) {
        Query query = queryRepository.findOneById(id);

        if (query == null) {
            throw new Query.NoSuchQueryException(id);
        }

        return query;
    }

    public Query updateQuery(long id, String searchString) {
        if (searchString.equals("")) {
            throw new Query.EmptyQuerySearchStringException();
        }

        Query query = queryRepository.findOneById(id);

        if (query == null) {
            throw new Query.NoSuchQueryException(id);
        }

        query.setSearchString(searchString);
        queryRepository.save(query);

        return query;
    }

}
