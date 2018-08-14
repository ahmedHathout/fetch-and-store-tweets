package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.ParameterOutOfBoundsException;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Singleton
public class QueryService {

    private AtomicLong topId;
    private QueryRepository queryRepository;

    @Autowired
    private QueryService(QueryRepository queryRepository) {

        try {
            this.topId = new AtomicLong(queryRepository.findFirstByOrderByIdDesc().getId());
        }
        catch (ParameterOutOfBoundsException e) {
            e.printStackTrace();
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
            return emptyQuery();
        }
        Query query = new Query(topId.incrementAndGet(), searchString);

        queryRepository.save(query);
        return query;
    }

    public Query getQueryById(long id) {
        Query query = queryRepository.findOneById(id);

        if (query == null)
            query = noSuchQuery();

        return query;
    }

    public Query updateQuery(long id, String searchString) {
        Query query = queryRepository.findOneById(id);

        if (query != null) {
            query.setSearchString(searchString);
            queryRepository.save(query);
        }

        else
            query = noSuchQuery();

        return query;
    }

    private Query noSuchQuery() {
        return new Query(-1L, "No such query bro :-)");
    }

    private Query emptyQuery() {
        return new Query(-1L, "The searchString is empty!");
    }
}
