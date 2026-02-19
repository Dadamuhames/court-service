package com.uzumtech.court.service;

import com.uzumtech.court.dto.response.OffenseDetailResponse;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.JudgeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JudgeOffenseService {
    Page<OffenseResponse> list(JudgeEntity judge, Pageable pageable);

    OffenseDetailResponse getOne(Long id, JudgeEntity judge);

    void sendForAiDecisionProcessing(Long id, JudgeEntity judge);
}
