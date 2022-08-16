package com.redeyefrog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class RestResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        boolean isBadRequest = response.getStatusCode().equals(HttpStatus.BAD_REQUEST);

        return !isBadRequest && super.hasError(response);// ignore bad request, control HttpStatusCodeException
    }

}
