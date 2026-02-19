package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.kafka.producer.AiDecisionEventProducer;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.event.AiDecisionEvent;
import com.uzumtech.court.dto.response.OffenseDetailResponse;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.dto.response.PenaltyDetailResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.exception.offense.OffenceNotFoundOrStatusInvalidException;
import com.uzumtech.court.exception.offense.OffenseNotFoundException;
import com.uzumtech.court.mapper.OffenseMapper;
import com.uzumtech.court.mapper.PenaltyMapper;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.service.JudgeOffenseService;
import com.uzumtech.court.service.OffenseHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JudgeOffenseServiceImpl implements JudgeOffenseService {
    private final OffenseRepository offenseRepository;
    private final OffenseMapper offenseMapper;
    private final PenaltyMapper penaltyMapper;
    private final AiDecisionEventProducer decisionEventProducer;
    private final OffenseHelperService offenseHelperService;

    @Transactional(readOnly = true)
    public Page<OffenseResponse> list(JudgeEntity judge, Pageable pageable) {
        Page<OffenseEntity> offenses = offenseRepository.findByJudgeId(judge.getId(), pageable);

        return offenses.map(offenseMapper::entityToResponse);
    }

    public OffenseEntity getByIdAndJudge(Long id, Long judgeId) {
        return offenseRepository.findByIdAndJudgeId(id, judgeId).orElseThrow(() -> new OffenseNotFoundException(ErrorCode.OFFENSE_NOT_FOUND_CODE));
    }

    @Transactional(readOnly = true)
    public OffenseDetailResponse getOne(Long id, JudgeEntity judge) {

        OffenseEntity offense = getByIdAndJudge(id, judge.getId());

        PenaltyDetailResponse penalty = penaltyMapper.entityToDetailResponse(offense.getPenalty());

        return offenseMapper.entityToDetailResponse(offense, penalty);
    }

    public void sendForAiDecisionProcessing(Long id, JudgeEntity judge) {
        boolean markedForProcessing = offenseHelperService.markForAiDecisionProcessing(id, judge.getId());

        if (!markedForProcessing) {
            throw new OffenceNotFoundOrStatusInvalidException(ErrorCode.OFFENSE_CANT_BE_SENT_TO_AI_DECISION_PROCESSING);
        }

        AiDecisionEvent event = AiDecisionEvent.of(id, judge.getEmail());

        decisionEventProducer.publishAiDecisionEvent(event);
    }
}