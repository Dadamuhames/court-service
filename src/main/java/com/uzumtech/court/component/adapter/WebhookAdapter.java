package com.uzumtech.court.component.adapter;

import com.uzumtech.court.dto.request.PenaltyWebhookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookAdapter {

    private final RestClient restClient;

    public void sendWebhook(PenaltyWebhookDto webhookDto) {

        log.info("Webhook Url: {}, Request: {}", webhookDto.webhookUrl(), webhookDto.request());

        var response = restClient.post()
            .uri(webhookDto.webhookUrl())
            .header("X-Court-Secret", webhookDto.webhookSecret())
            .body(webhookDto.request())
            .retrieve()
            .toBodilessEntity();


        log.info("Webhook Response Status: {}", response.getStatusCode());
    }

}
