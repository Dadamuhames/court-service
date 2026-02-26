package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.kafka.producer.PenaltyWebhookEventProducer;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.dto.request.penalty.PenaltyRequest;
import com.uzumtech.court.dto.request.penalty.PenaltyUpdateRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.BhmAmountEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import com.uzumtech.court.exception.judge.JudgeAccessDeniedException;
import com.uzumtech.court.exception.offense.OffenseNotFoundException;
import com.uzumtech.court.exception.penalty.PenaltyExistsException;
import com.uzumtech.court.exception.penalty.PenaltyStatusInvalidException;
import com.uzumtech.court.mapper.PenaltyMapper;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.repository.PenaltyRepository;
import com.uzumtech.court.service.BhmService;
import com.uzumtech.court.service.PenaltyHelperService;
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
    private final PenaltyHelperService penaltyHelperService;
    private final BhmService bhmService;

    public PenaltyResponse ruleOutPenalty(PenaltyRequest request, JudgeEntity judge) {
        validateOffenseId(request.offenseId());

        OffenseEntity offenseEntity = offenseRepository.findById(request.offenseId()).orElseThrow(() -> new OffenseNotFoundException(ErrorCode.OFFENSE_NOT_FOUND_CODE));

        if (!offenseEntity.getJudge().getId().equals(judge.getId())) {
            throw new JudgeAccessDeniedException(ErrorCode.JUDGE_ACCESS_DENIED_CODE);
        }

        BhmAmountEntity bhmAmount = bhmService.getCurrentAmount();

        PenaltyEntity penalty = penaltyMapper.requestToEntity(request, offenseEntity, bhmAmount.getAmount());

        PenaltyEntity savedPenalty = penaltyHelperService.savePenalty(penalty);

        sendPenaltyEvent(savedPenalty.getId());

        return penaltyMapper.entityToResponse(savedPenalty);
    }


    private void validateOffenseId(Long offenseId) {
        boolean penaltyExists = penaltyRepository.existsByOffenseId(offenseId);

        if (penaltyExists) {
            throw new PenaltyExistsException(ErrorCode.PENALTY_EXISTS_CODE);
        }
    }

    public void confirmPenalty(Long penaltyId, JudgeEntity judge) {
        PenaltyEntity penalty = penaltyHelperService.getByIdAndJudgeId(penaltyId, judge.getId());

        if (!penalty.getStatus().equals(PenaltyStatus.DRAFT)) {
            throw new PenaltyStatusInvalidException(ErrorCode.PENALTY_STATUS_INVALID_CODE);
        }

        penalty.setStatus(PenaltyStatus.CONFIRMED);

        penaltyHelperService.savePenalty(penalty);

        sendPenaltyEvent(penaltyId);
    }

    @Transactional
    public PenaltyResponse updatePenaltyAndConfirm(Long penaltyId, PenaltyUpdateRequest request, JudgeEntity judge) {
        PenaltyEntity penalty = penaltyHelperService.getByIdAndJudgeId(penaltyId, judge.getId());

        if (penalty.getStatus() == PenaltyStatus.CONFIRMED) {
            throw new PenaltyStatusInvalidException(ErrorCode.PENALTY_STATUS_INVALID_CODE);
        }

        penaltyMapper.updatePenalty(request, penalty);

        PenaltyEntity savedPenalty = penaltyRepository.save(penalty);

        return penaltyMapper.entityToResponse(savedPenalty);
    }


    private PenaltyEntity getByOffenseIdOrBuild(Long offenseId) {
        return penaltyRepository.findByOffenseId(offenseId).orElse(PenaltyEntity.builder().status(PenaltyStatus.DRAFT).offense(offenseRepository.getReferenceById(offenseId)).build());
    }

    @Transactional
    public void ruleOutFromAiDecision(Long offenseId, DecisionOutput output) {
        PenaltyEntity penalty = getByOffenseIdOrBuild(offenseId);

        PenaltyStatus penaltyStatus = penalty.getStatus();

        if (penaltyStatus == PenaltyStatus.CONFIRMED || penaltyStatus == PenaltyStatus.SENT) {
            throw new PenaltyStatusInvalidException(ErrorCode.PENALTY_STATUS_INVALID_CODE);
        }

        BhmAmountEntity bhmAmount = bhmService.getCurrentAmount();

        penaltyMapper.aiDecisionToPenalty(output, bhmAmount.getAmount(), penalty);

        penaltyRepository.save(penalty);

        offenseRepository.updateStatus(offenseId, OffenseStatus.DRAFT_PENALTY);
    }

    private void sendPenaltyEvent(Long penaltyId) {
        PenaltyWebhookEvent webhookEvent = new PenaltyWebhookEvent(penaltyId);

        penaltyWebhookEventProducer.publishWebhookEvent(webhookEvent);
    }

    @Transactional
    public void changeStatus(Long id, PenaltyStatus status) {
        penaltyRepository.updateStatus(id, status);
    }
}
