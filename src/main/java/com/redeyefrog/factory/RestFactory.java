package com.redeyefrog.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestFactory {

    @Autowired
    private RestTemplate restTemplate;

    public <Q, S> ResponseEntity<S> callTelegram(String url, Q request, Class<S> responseClass) {
        return restTemplate.postForEntity(url, getHttpEntity(request), responseClass);
    }

    private <Q> HttpEntity<Q> getHttpEntity(Q request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(request, httpHeaders);
    }

}
