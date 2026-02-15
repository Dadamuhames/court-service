package com.uzumtech.court.configuration;

import com.uzumtech.court.configuration.property.GcpServiceProperties;
import com.uzumtech.court.handler.RestClientExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.HttpClientSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.Base64;

@Configuration
@RequiredArgsConstructor
public class RestClientConfiguration {
    private final GcpServiceProperties gcpServiceProperties;

    @Bean(name = "gcpRestClient")
    public RestClient gcpRestClient(RestClient.Builder builder) {
        String authToken = getGcpAuthToken();

        return builder.requestFactory(clientHttpRequestFactory())
            .defaultStatusHandler(new RestClientExceptionHandler())
            .baseUrl(gcpServiceProperties.getUrl())
            .defaultHeader("Authorization", String.format("Basic %s", authToken))
            .build();
    }

    @Bean(name = "restClient")
    public RestClient restClient(RestClient.Builder builder) {

        return builder.requestFactory(clientHttpRequestFactory())
            .defaultStatusHandler(new RestClientExceptionHandler())
            .build();
    }

    private String getGcpAuthToken() {
        String authTokenRaw = String.format("%s:%s", gcpServiceProperties.getLogin(), gcpServiceProperties.getPassword());

        return Base64.getEncoder().encodeToString(authTokenRaw.getBytes());
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        var settings = HttpClientSettings.defaults().withReadTimeout(Duration.ofMillis(1000)).withConnectTimeout(Duration.ofMillis(5000));

        return ClientHttpRequestFactoryBuilder.jdk().build(settings);
    }
}
