package com.uzumtech.court.component.kafka.producer;

import com.uzumtech.court.constant.KafkaConstants;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PenaltyWebhookEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishWebhookEvent(PenaltyWebhookEvent webhookEvent) {
        kafkaTemplate.send(KafkaConstants.WEBHOOK_TOPIC, webhookEvent);
    }
}
