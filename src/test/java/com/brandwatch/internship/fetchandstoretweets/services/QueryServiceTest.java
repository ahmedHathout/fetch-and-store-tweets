package com.brandwatch.internship.fetchandstoretweets.services;

import com.brandwatch.internship.fetchandstoretweets.entities.Query;
import com.brandwatch.internship.fetchandstoretweets.repositories.QueryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class QueryServiceTest {

    private static final String EMPTY_SEARCH_STRING = "";

    @Mock
    private QueryRepository queryRepository;

    @InjectMocks
    private QueryService queryService;

    @Before
    public void mockQueryRepository() {
        when(queryRepository.findOneById(anyLong())).thenReturn(null);
    }

    @Test(expected = Query.EmptyQuerySearchStringException.class)
    public void createQueryExceptionTest() {
        queryService.createQuery(EMPTY_SEARCH_STRING);
    }

    @Test(expected = Query.NoSuchQueryException.class)
    public void getQueryByIdExceptionTest() {
        queryService.getQueryById(anyLong());
    }

    @Test(expected = Query.EmptyQuerySearchStringException.class)
    public void updateQueryWithEmptyStringExceptionTest() {
        queryService.updateQuery(anyLong(), EMPTY_SEARCH_STRING);
    }

    @Test(expected = Query.NoSuchQueryException.class)
    public void updateNonExistingQueryExceptionTest() {
        queryService.updateQuery(anyLong(), "Any valid searchString");
    }
}
