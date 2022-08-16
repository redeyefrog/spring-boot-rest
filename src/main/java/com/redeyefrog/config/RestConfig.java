package com.redeyefrog.config;

import com.redeyefrog.interceptor.RestClientHttpRequestInterceptor;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;

@Configuration
public class RestConfig {

    @Autowired
    private RestClientHttpRequestInterceptor restClientHttpRequestInterceptor;

    @Bean
    RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        restTemplate.getInterceptors().add(restClientHttpRequestInterceptor);
//        restTemplate.setErrorHandler(new RestResponseErrorHandler());
        return restTemplate;
    }

    @Bean
    ClientHttpRequestFactory getClientHttpRequestFactory(HttpClient httpClient) throws Exception {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(60 * 1000);
        factory.setReadTimeout(30 * 1000);
        factory.setConnectionRequestTimeout(60 * 1000);
        return new BufferingClientHttpRequestFactory(factory);
    }

    @Bean
    HttpClient getHttpClient(HttpClientConnectionManager httpClientConnectionManager) {
        return HttpClientBuilder.create()
                .setConnectionManager(httpClientConnectionManager)
                .build();
    }

    @Bean
    HttpClientConnectionManager getHttpClientConnectionManager(Registry<ConnectionSocketFactory> connectionSocketFactoryRegistry) throws Exception {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(connectionSocketFactoryRegistry);
        httpClientConnectionManager.setDefaultMaxPerRoute(1000);
        httpClientConnectionManager.setMaxTotal(100);
        return httpClientConnectionManager;
    }

    @Bean
    Registry<ConnectionSocketFactory> connectionSocketFactoryRegistry(SSLConnectionSocketFactory sslConnectionSocketFactory) throws Exception {
        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory)
                .build();
    }

    @Bean
    SSLConnectionSocketFactory getSSLConnectionSocketFactory() throws Exception {
        SSLContext sslContext = SSLContextBuilder.create()
                .setProtocol("TLS")
                .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                .build();
        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }

}
