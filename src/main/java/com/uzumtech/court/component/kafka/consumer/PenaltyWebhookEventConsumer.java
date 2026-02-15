package com.uzumtech.court.component.kafka.consumer;

import com.uzumtech.court.component.kafka.EventConsumer;
import com.uzumtech.court.constant.KafkaConstants;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.exception.http.HttpClientException;
import com.uzumtech.court.exception.http.HttpServerException;
import com.uzumtech.court.exception.kafka.nontransients.HttpRequestInvalidException;
import com.uzumtech.court.exception.kafka.transients.HttpServerUnavailableException;
import com.uzumtech.court.exception.kafka.transients.TransientException;
import com.uzumtech.court.service.ExternalWebhookService;
import com.uzumtech.court.service.PenaltyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PenaltyWebhookEventConsumer implements EventConsumer<PenaltyWebhookEvent> {
    private final PenaltyService penaltyService;
    private final ExternalWebhookService externalWebhookService;

    @RetryableTopic(attempts = "6", backoff = @Backoff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.WEBHOOK_TOPIC, groupId = KafkaConstants.WEBHOOK_GROUP)
    public void listen(@Payload @Valid PenaltyWebhookEvent event) {
        try {
            externalWebhookService.sendToWebhook(event);

            penaltyService.changeStatus(event.id(), PenaltyStatus.SENT);

        } catch (HttpServerException e) {
            throw new HttpServerUnavailableException(e);

        } catch (HttpClientException e) {
            throw new HttpRequestInvalidException(ErrorCode.EXTERNAL_WEBHOOK_REQUEST_INVALID_CODE, e);
        }
    }

    @Override
    public void dltHandler(PenaltyWebhookEvent event, String exceptionMessage) {
        log.info("Penalty Webhook request failed. Id: {}, Reason: {}", event.id(), exceptionMessage);

        penaltyService.changeStatus(event.id(), PenaltyStatus.FAILED);
    }
}
