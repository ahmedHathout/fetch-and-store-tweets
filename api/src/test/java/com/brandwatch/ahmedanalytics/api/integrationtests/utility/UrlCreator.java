package com.brandwatch.ahmedanalytics.api.integrationtests.utility;

import org.springframework.boot.test.web.client.TestRestTemplate;

public class UrlCreator {

    private final TestRestTemplate restTemplate;
    private String baseUri;
    private final int port;

    public UrlCreator(TestRestTemplate testRestTemplate, String baseUri, int port) {
        this.restTemplate = testRestTemplate;
        this.baseUri = baseUri;
        this.port = port;
    }

    public String createURLWithPort(String uri) {
        return "http://localhost:" + port + baseUri + uri;
    }
}
