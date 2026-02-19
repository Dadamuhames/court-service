package com.uzumtech.court.service.impl;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyWebhookDto;
import com.uzumtech.court.dto.request.PenaltyWebhookRequest;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import com.uzumtech.court.exception.penalty.PenaltyNotFoundException;
import com.uzumtech.court.mapper.PenaltyMapper;
import com.uzumtech.court.repository.PenaltyRepository;
import com.uzumtech.court.service.PenaltyHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PenaltyHelperServiceImpl implements PenaltyHelperService {

    private final PenaltyRepository penaltyRepository;
    private final PenaltyMapper penaltyMapper;

    @Transactional
    public PenaltyEntity savePenalty(PenaltyEntity entity) {
        return penaltyRepository.save(entity);
    }

    @Transactional(readOnly = true)
    public PenaltyEntity getByIdAndJudgeId(Long penaltyId, Long judgeId) {
        return penaltyRepository.findByIdAndJudgeId(penaltyId, judgeId).orElseThrow(() -> new PenaltyNotFoundException(ErrorCode.PENALTY_NOT_FOUND_CODE));
    }

    @Transactional(readOnly = true)
    public PenaltyWebhookDto getWebhookDto(PenaltyWebhookEvent event) {
        PenaltyEntity penalty = penaltyRepository.findById(event.penaltyId()).orElseThrow();

        ExternalServiceEntity externalService = penalty.getOffense().getExternalService();

        PenaltyWebhookRequest request = penaltyMapper.entityToWebhookRequest(penalty, externalService.getId());

        return new PenaltyWebhookDto(externalService.getWebhookUrl(), externalService.getWebhookSecret(), request);
    }
}
