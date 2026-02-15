package com.uzumtech.court.dto.llm;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.constant.enums.PenaltyType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyDto(PenaltyStatus status,
                         PenaltyType type,
                         BigDecimal bhmMultiplier,
                         OffsetDateTime dueDate,
                         String courtDecisionText) {
}
