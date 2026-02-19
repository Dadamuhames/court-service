package com.uzumtech.court.service.impl;

import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.service.OffenseHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OffenseHelperServiceImpl implements OffenseHelperService {
    private final OffenseRepository offenseRepository;

    @Transactional
    public boolean markForAiDecisionProcessing(Long id, Long judgeId) {
        return offenseRepository.markForAiDecisionProcessing(id, judgeId) == 1;
    }
}
