package com.uzumtech.court.component.kafka.consumer;

import com.uzumtech.court.dto.event.NotificationEvent;
import com.uzumtech.court.component.adapter.NotificationAdapter;
import com.uzumtech.court.constant.KafkaConstants;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.NotificationRequestStatus;
import com.uzumtech.court.dto.request.NotificationRequest;
import com.uzumtech.court.dto.response.NotificationResponse;
import com.uzumtech.court.exception.http.HttpClientException;
import com.uzumtech.court.exception.http.HttpServerException;
import com.uzumtech.court.exception.kafka.nontransients.HttpRequestInvalidException;
import com.uzumtech.court.exception.kafka.transients.HttpServerUnavailableException;
import com.uzumtech.court.exception.kafka.transients.TransientException;
import com.uzumtech.court.service.NotificationRequestStoreService;
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
public class NotificationConsumer implements EventConsumer<NotificationEvent> {
    private final NotificationAdapter notificationAdapter;
    private final NotificationRequestStoreService requestStoreService;

    @RetryableTopic(attempts = "6", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.NOTIFICATION_TOPIC, groupId = KafkaConstants.NOTIFICATION_GROUP_ID)
    public void listen(@Payload @Valid NotificationEvent event) {
        int claim = requestStoreService.claimForProcessing(event.requestId());

        if (claim == 0) {
            log.info("Notification with requestId: {} is already being processed or in the terminal status", event.requestId());
            return;
        }

        NotificationRequest request = NotificationRequest.of(event.receiver(), event.text(), event.type());

        try {
            NotificationResponse response = notificationAdapter.sendNotification(request);

            requestStoreService.markAsDelivered(event.requestId(), response.data().notificationId());

        } catch (HttpServerException ex) {
            requestStoreService.changeStatus(event.requestId(), NotificationRequestStatus.SENT_TO_RETRY);

            log.error("Notification Http Server Error: {}", ex.getMessage());

            throw new HttpServerUnavailableException(ex);

        } catch (HttpClientException ex) {

            log.error("Notification Http Client Error: {}", ex.getMessage());

            throw new HttpRequestInvalidException(ErrorCode.NOTIFICATION_REQUEST_INVALID_CODE, ex);
        }
    }

    @DltHandler
    public void dltHandler(NotificationEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.error("Event failed: {}, with exception: {}", event, exceptionMessage);
        requestStoreService.changeStatus(event.requestId(), NotificationRequestStatus.FAILED);
    }
}
