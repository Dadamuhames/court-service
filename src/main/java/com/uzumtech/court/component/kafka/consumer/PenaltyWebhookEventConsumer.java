package com.uzumtech.court.component.kafka.consumer;

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
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PenaltyWebhookEventConsumer implements EventConsumer<PenaltyWebhookEvent> {
    private final PenaltyService penaltyService;
    private final ExternalWebhookService externalWebhookService;

    @KafkaListener(topics = KafkaConstants.WEBHOOK_TOPIC, groupId = KafkaConstants.WEBHOOK_GROUP)
    @RetryableTopic(attempts = "6", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    public void listen(@Payload @Valid PenaltyWebhookEvent event) {

        log.info("Webhook event: {}", event);

        try {
            externalWebhookService.sendToWebhook(event);

            log.info("Webhook sent");

            penaltyService.changeStatus(event.penaltyId(), PenaltyStatus.SENT);

        } catch (HttpServerException e) {

            log.info("Http Server Error: {}", e.getMessage());

            throw new HttpServerUnavailableException(e);

        } catch (HttpClientException e) {

            log.info("Http Client Error: {}", e.getMessage());

            throw new HttpRequestInvalidException(ErrorCode.EXTERNAL_WEBHOOK_REQUEST_INVALID_CODE, e);
        }
    }

    @DltHandler
    public void dltHandler(PenaltyWebhookEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.info("Penalty Webhook request failed. Id: {}, Reason: {}", event.penaltyId(), exceptionMessage);

        penaltyService.changeStatus(event.penaltyId(), PenaltyStatus.FAILED);
    }
}
