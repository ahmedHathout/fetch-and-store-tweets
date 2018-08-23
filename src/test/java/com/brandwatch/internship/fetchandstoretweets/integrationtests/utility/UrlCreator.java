package com.brandwatch.internship.fetchandstoretweets.integrationtests.utility;

import jdk.internal.org.objectweb.asm.TypeReference;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.net.URI;

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
