package com.uzumtech.court.dto.llm;

import com.uzumtech.court.constant.enums.PenaltyType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record DecisionOutput(
    String qualification,
    PenaltyType type,
    BigDecimal bhmMultiplier,
    OffsetDateTime dueDate,
    String courtDecisionText,
    Integer deprivationDurationMonths
) {
}
