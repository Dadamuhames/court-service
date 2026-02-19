package com.uzumtech.court.component.kafka.consumer;

import com.uzumtech.court.component.kafka.producer.EmailEventPublisher;
import com.uzumtech.court.constant.KafkaConstants;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.dto.event.AiDecisionEvent;
import com.uzumtech.court.dto.event.OnAiDecisionEmailEvent;
import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.exception.kafka.nontransients.OffenseStatusInvalidException;
import com.uzumtech.court.exception.kafka.transients.TransientException;
import com.uzumtech.court.service.DecisionLlmService;
import com.uzumtech.court.service.OffenseService;
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
public class AiDecisionEventConsumer implements EventConsumer<AiDecisionEvent> {

    private final DecisionLlmService decisionLlmService;
    private final EmailEventPublisher emailEventPublisher;
    private final OffenseService offenseService;
    private final PenaltyService penaltyService;

    @KafkaListener(topics = KafkaConstants.AI_DECISION_TOPIC, groupId = KafkaConstants.AI_DECISION_GROUP)
    @RetryableTopic(backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    public void listen(@Payload @Valid AiDecisionEvent event) {

        log.info("Ai event being processed: {}", event);

        boolean offenseExistsAndAvailable = offenseService.existsByIdAndProcessing(event.offenseId());

        log.info("offenseExistsAndAvailable: {}", offenseExistsAndAvailable);

        if (!offenseExistsAndAvailable) {
            throw new OffenseStatusInvalidException(ErrorCode.OFFENSE_ID_INVALID_OR_CLOSED);
        }

        DecisionOutput llmOutput = decisionLlmService.getAIDecision(event.offenseId());

        log.info("AI output: {}", llmOutput);

        penaltyService.ruleOutFromAiDecision(event.offenseId(), llmOutput);

        OnAiDecisionEmailEvent emailEvent = new OnAiDecisionEmailEvent(event.judgeEmail(), event.offenseId());

        emailEventPublisher.publish(emailEvent);
    }

    @DltHandler
    public void dltHandler(AiDecisionEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        log.info("Offense AI decision request failed. Id: {}, Reason: {}", event.offenseId(), exceptionMessage);

        offenseService.changeStatus(event.offenseId(), OffenseStatus.PENDING);
    }
}
