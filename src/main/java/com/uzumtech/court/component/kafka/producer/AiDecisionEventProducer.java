package com.uzumtech.court.component.kafka.producer;

import com.uzumtech.court.constant.KafkaConstants;
import com.uzumtech.court.dto.event.AiDecisionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiDecisionEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishAiDecisionEvent(AiDecisionEvent event) {
        kafkaTemplate.send(KafkaConstants.AI_DECISION_TOPIC, event);
    }
}
