package com.uzumtech.court.component.kafka.producer;

import com.uzumtech.court.constant.KafkaConstants;
import com.uzumtech.court.constant.NotificationTemplates;
import com.uzumtech.court.constant.enums.NotificationType;
import com.uzumtech.court.dto.event.NotificationEvent;
import com.uzumtech.court.dto.event.OnAiDecisionEmailEvent;
import com.uzumtech.court.service.NotificationRequestStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmailEventPublisher implements KafkaEventPublisher<OnAiDecisionEmailEvent> {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NotificationRequestStoreService requestStoreService;

    @Override
    public void publish(OnAiDecisionEmailEvent event) {
        UUID requestId = UUID.randomUUID();

        String notificationText = String.format(NotificationTemplates.AI_DECISION_TEMPLATE, event.offenseId());

        var notificationEvent = new NotificationEvent(requestId, notificationText, event.email(), NotificationType.EMAIL);

        requestStoreService.createNotificationRequest(notificationEvent);

        kafkaTemplate.send(KafkaConstants.NOTIFICATION_TOPIC, notificationEvent);
    }
}
