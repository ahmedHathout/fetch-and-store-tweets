package com.brandwatch.ahmedanalytics.common.servicestests;

import com.brandwatch.ahmedanalytics.common.entities.Query;
import com.brandwatch.ahmedanalytics.common.repositories.QueryRepository;
import com.brandwatch.ahmedanalytics.common.services.QueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QueryServiceTest {

    private static final long QUERY_ID = 2;
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
        queryService.updateQuery(QUERY_ID, EMPTY_SEARCH_STRING);
    }

    @Test(expected = Query.NoSuchQueryException.class)
    public void updateNonExistingQueryExceptionTest() {
        queryService.updateQuery(QUERY_ID, "Any valid searchString");
    }
}
