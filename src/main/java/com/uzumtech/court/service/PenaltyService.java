package com.uzumtech.court.service;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.dto.request.penalty.PenaltyRequest;
import com.uzumtech.court.dto.request.penalty.PenaltyUpdateRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.JudgeEntity;

public interface PenaltyService {
    PenaltyResponse ruleOutPenalty(final PenaltyRequest request, final JudgeEntity judge);

    PenaltyResponse updatePenaltyAndConfirm(Long penaltyId, PenaltyUpdateRequest request, JudgeEntity judge);

    void confirmPenalty(Long penaltyId, JudgeEntity judge);

    void changeStatus(final Long id, final PenaltyStatus status);

    void ruleOutFromAiDecision(Long offenseId, DecisionOutput output);
}
