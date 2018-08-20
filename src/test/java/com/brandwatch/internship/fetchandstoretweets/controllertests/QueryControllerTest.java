package com.brandwatch.internship.fetchandstoretweets.controllertests;

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
public class QueryControllerTest {

    private static final long queryId = 3;
    private static final String searchString = "test";

    @Autowired
    private QueryController queryController;

    @Autowired
    private QueryRepository queryRepository;


    @Test
    public void getAllQueriesTest() {
        assertEquals(queryController.getAllQueries(), queryRepository.findAll());
        // This is because I am not sure if equals() is implemented.
        assertEquals(queryController.getAllQueries().size(), queryRepository.findAll().size());
    }

    @Test
    public void getQueryByIdTest() {
        try {
            assertEquals(queryController.getQueryById(queryId), queryRepository.findOneById(queryId));
        } catch (NullPointerException e) {
            assertNull(queryRepository.findOneById(queryId));
        }
    }

    @Test
    public void createQueryTest() {
        long newQueryId = queryController.createQuery(searchString).getId();

        Query query = queryRepository.findOneById(newQueryId);

        assertNotNull(query);
        queryRepository.delete(query);
    }

    @Test
    public void updateQueryTest() {
        String newSearchString = "new test";
        long newQueryId = queryController.createQuery(searchString).getId();

        queryController.updateQuery(newQueryId, newSearchString);

        assertEquals(queryRepository.findOneById(newQueryId).getSearchString(), newSearchString);
    }

}
