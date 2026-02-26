package com.uzumtech.court.service;

import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.penalty.PenaltyWebhookDto;
import com.uzumtech.court.entity.PenaltyEntity;

public interface PenaltyHelperService {
    PenaltyEntity savePenalty(PenaltyEntity entity);

    PenaltyWebhookDto getWebhookDto(PenaltyWebhookEvent event);

    PenaltyEntity getByIdAndJudgeId(Long penaltyId, Long judgeId);
}
