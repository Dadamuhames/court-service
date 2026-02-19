package com.uzumtech.court.configuration;

import com.uzumtech.court.configuration.property.GcpServiceProperties;
import com.uzumtech.court.configuration.property.NotificationServiceProperties;
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
    private final NotificationServiceProperties notificationServiceProperties;

    @Bean(name = "notificationRestClient")
    public RestClient notificationRestClient(RestClient.Builder builder) {
        String authToken = getNotificationAuthToken();

        return builder.requestFactory(clientHttpRequestFactory())
            .defaultStatusHandler(new RestClientExceptionHandler())
            .baseUrl(notificationServiceProperties.getUrl())
            .defaultHeader("Authorization", String.format("Basic %s", authToken))
            .build();
    }

    private String getNotificationAuthToken() {
        String authTokenRaw = String.format("%s:%s", notificationServiceProperties.getLogin(), notificationServiceProperties.getPassword());

        return Base64.getEncoder().encodeToString(authTokenRaw.getBytes());
    }

    @Bean(name = "gcpRestClient")
    public RestClient gcpRestClient(RestClient.Builder builder) {

        return builder.requestFactory(clientHttpRequestFactory())
            .defaultStatusHandler(new RestClientExceptionHandler())
            .baseUrl(gcpServiceProperties.getUrl())
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
        var settings = HttpClientSettings.defaults().withReadTimeout(Duration.ofMillis(5000)).withConnectTimeout(Duration.ofMillis(5000));

        return ClientHttpRequestFactoryBuilder.jdk().build(settings);
    }
}
