package com.redeyefrog.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RestClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = null;
        try {
            logRequest(request, body);
            response = execution.execute(request, body);
        } finally {
            logResponse(response);
        }
        return response;
    }

    private void logRequest(HttpRequest request, byte[] content) throws IOException {
        log.debug("    URI: {}", request.getURI());
        log.debug(" Method: {}", request.getMethod());
        log.debug("Headers: {}", request.getHeaders());
        log.debug("Request: {}", customLog(content));
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (response != null) {
            log.debug("Status Code: {}", response.getStatusCode());
            log.debug("Status Text: {}", response.getStatusText());
            log.debug("    Headers: {}", response.getHeaders());
            log.debug("   Response: {}", StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8));
        }
    }

    private String customLog(byte[] content) throws JsonProcessingException {
        String body = new String(content, StandardCharsets.UTF_8);
        ObjectNode objectNode = objectMapper.readValue(body, ObjectNode.class);
        objectNode.remove("fileContent");// not print this attribute
        return objectMapper.writeValueAsString(objectNode);
    }

}
