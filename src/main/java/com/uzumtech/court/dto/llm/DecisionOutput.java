package com.uzumtech.court.dto.llm;

import com.uzumtech.court.constant.enums.ConfidenceLevel;
import com.uzumtech.court.constant.enums.PenaltyType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record DecisionOutput(
    String decisionSummary,
    ProposedPenalty proposedPenalty,
    String additionalPenaltyInfo,
    String reasoning,
    List<String> mitigatingCircumstances,
    List<String> aggravatingCircumstances,
    String recommendation,
    ConfidenceLevel confidence,
    List<String> missingInformation
) {

    public record ProposedPenalty(
        String qualification,
        PenaltyType type,
        BigDecimal bhmMultiplier,
        OffsetDateTime dueDate,
        String courtDecisionText,
        Integer deprivationDurationMonths
    ) {
    }
}
