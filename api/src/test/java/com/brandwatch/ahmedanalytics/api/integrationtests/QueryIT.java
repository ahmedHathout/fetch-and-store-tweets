package com.brandwatch.ahmedanalytics.api.integrationtests;

import com.brandwatch.ahmedanalytics.ApiApplication;
import com.brandwatch.ahmedanalytics.common.entities.Query;
import com.brandwatch.ahmedanalytics.common.repositories.QueryRepository;
import com.brandwatch.ahmedanalytics.api.integrationtests.utility.UrlCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ApiApplication.class, TestConfig.class})

@TestPropertySource(locations="classpath:test.properties")
public class QueryIT {

    private static final long QUERY_ID = 3;
    private static final String BASE_URI = "/queries";

    @LocalServerPort
    private int port;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ObjectMapper mapper;

    private UrlCreator urlCreator;
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setUp() {
        urlCreator = new UrlCreator(restTemplate, BASE_URI, port);
        restTemplate.getRestTemplate().setRequestFactory(
                new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
    }

    @Test
    public void getAllQueriesTest() throws IOException {
        final String uri = "/";

        String responseBody = restTemplate.getForObject(urlCreator.createURLWithPort(uri), String.class);

        List<Query> actualQueries = mapper.readValue(responseBody, new TypeReference<List<Query>>() {});
        List<Query> expectedQueries = queryRepository.findAll();

        assertEquals(actualQueries, expectedQueries);
    }

    @Test
    public void getQueryByIdTest() {
        final String uri = "/" + QUERY_ID;

        Query actualQuery = restTemplate.getForObject(urlCreator.createURLWithPort(uri), Query.class);
        Query expectedQuery = queryRepository.findOneById(QUERY_ID);

        assertEquals(actualQuery, expectedQuery);
    }

    @Test
    public void createQueryTest() throws IOException {
        final String uri = "/";
        final String requestBody = "searchString=test";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        String responseBody = restTemplate.postForObject(urlCreator.createURLWithPort(uri), request, String.class);
        Query actualQuery = mapper.readValue(responseBody, Query.class);
        Query expectedQuery = queryRepository.findOneById(actualQuery.getId());

        assertEquals(expectedQuery, actualQuery);
        queryRepository.delete(actualQuery);
    }

    @Test
    public void updateQueryTest() throws IOException {
        final String uri = "/" + QUERY_ID;
        final String expectedString = "new test";
        final String requestBody = "searchString=" + expectedString.replaceAll(" ", "%20");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        String responseBody = restTemplate.patchForObject(urlCreator.createURLWithPort(uri), request, String.class);
        String actualSearchString = mapper.readValue(responseBody, Query.class).getSearchString();
        assertEquals(expectedString, actualSearchString);
    }

}
