package com.brandwatch.ahmedanalytics.api.integrationtests;

import com.brandwatch.ahmedanalytics.api.integrationtests.utility.UrlCreator;
import com.brandwatch.ahmedanalytics.common.entities.Mention;
import com.brandwatch.ahmedanalytics.common.repositories.MentionsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:test.properties")
public class MentionIT {

    private static final long QUERY_ID = 3;
    private static final String BASE_URI = "/mentions";

    @Autowired
    private MentionsRepository mentionsRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private UrlCreator urlCreator;

    @Before
    public void setUp() {
        urlCreator = new UrlCreator(restTemplate, BASE_URI, port);
    }

    @Test
    public void getMentionsByQueryIdTestNormal() throws IOException {
        final String uri = "/" + QUERY_ID;

        String responseBody = restTemplate.getForObject(urlCreator.createURLWithPort(uri), String.class);

        List<Mention> actualMentions= mapper.readValue(responseBody, new TypeReference<List<Mention>>() {});
        List<Mention> expectedMentions = mentionsRepository.findById_QueryId(QUERY_ID);

        assertEquals(expectedMentions, actualMentions);
    }

}
