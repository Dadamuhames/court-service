package com.uzumtech.court.service;

public interface OffenseHelperService {
    boolean markForAiDecisionProcessing(Long id, Long judgeId);
}
