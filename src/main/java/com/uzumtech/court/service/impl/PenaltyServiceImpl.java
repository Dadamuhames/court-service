package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.kafka.producer.PenaltyWebhookEventProducer;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.BhmAmountEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import com.uzumtech.court.exception.OffenseNotFoundException;
import com.uzumtech.court.mapper.PenaltyMapper;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.repository.PenaltyRepository;
import com.uzumtech.court.service.BhmService;
import com.uzumtech.court.service.PenaltyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PenaltyServiceImpl implements PenaltyService {
    private final OffenseRepository offenseRepository;
    private final PenaltyRepository penaltyRepository;
    private final PenaltyMapper penaltyMapper;
    private final PenaltyWebhookEventProducer penaltyWebhookEventProducer;
    private final BhmService bhmService;

    public PenaltyResponse ruleOutPenalty(final PenaltyRequest request, final JudgeEntity judge) {
        OffenseEntity offenseEntity = offenseRepository.findById(request.offenseId()).orElseThrow(() -> new OffenseNotFoundException(ErrorCode.OFFENSE_NOT_FOUND_CODE));

        BhmAmountEntity bhmAmount = bhmService.getCurrentAmount();

        PenaltyEntity penalty = penaltyMapper.requestToEntity(request, judge, offenseEntity, bhmAmount.getAmount());

        PenaltyEntity savedPenalty = savePenalty(penalty);

        PenaltyWebhookEvent webhookEvent = penaltyMapper.entityToWebhookEvent(savedPenalty);

        penaltyWebhookEventProducer.publishWebhookEvent(webhookEvent);

        return penaltyMapper.entityToResponse(savedPenalty);
    }

    @Transactional
    private PenaltyEntity savePenalty(PenaltyEntity entity) {
        return penaltyRepository.save(entity);
    }

    @Transactional
    public void changeStatus(final Long id, final PenaltyStatus status) {
        penaltyRepository.updateStatus(id, status);
    }
}
