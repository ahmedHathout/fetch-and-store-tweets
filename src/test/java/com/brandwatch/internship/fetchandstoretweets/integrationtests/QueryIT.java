package com.brandwatch.internship.fetchandstoretweets.integrationtests;

import com.brandwatch.internship.fetchandstoretweets.controllers.QueryController;
import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class QueryIT {

    private static final long QUERY_ID = 3;
    private static final String SEARCH_STRING = "test";

    @Autowired
    private QueryController queryController;

    @Autowired
    private QueryRepository queryRepository;


    @Test
    public void getAllQueriesTest() {
        assertEquals(queryController.getAllQueries(), queryRepository.findAll());
    }

    @Test
    public void getQueryByIdTest() {
        try {
            assertEquals(queryController.getQueryById(QUERY_ID), queryRepository.findOneById(QUERY_ID));
        } catch (NullPointerException e) {
            assertNull(queryRepository.findOneById(QUERY_ID));
        }
    }

    @Test
    public void createQueryTest() {
        long newQueryId = queryController.createQuery(SEARCH_STRING).getId();

        Query query = queryRepository.findOneById(newQueryId);

        assertNotNull(query);
        queryRepository.delete(query);
    }

    @Test
    public void updateQueryTest() {
        String newSearchString = "new test";
        long newQueryId = queryController.createQuery(SEARCH_STRING).getId();

        queryController.updateQuery(newQueryId, newSearchString);

        assertEquals(queryRepository.findOneById(newQueryId).getSearchString(), newSearchString);
    }

}
