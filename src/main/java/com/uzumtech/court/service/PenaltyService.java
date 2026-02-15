package com.uzumtech.court.service;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.JudgeEntity;

public interface PenaltyService {
    PenaltyResponse ruleOutPenalty(final PenaltyRequest request, final JudgeEntity judge);

     void changeStatus(final Long id, final PenaltyStatus status);
}
