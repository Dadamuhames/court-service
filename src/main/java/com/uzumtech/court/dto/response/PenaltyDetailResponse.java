package com.uzumtech.court.dto.response;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.constant.enums.PenaltyType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyDetailResponse(Long id,
                                    PenaltyType type,
                                    PenaltyStatus status,
                                    Long bhmAmountAtTime,
                                    BigDecimal bhmMultiplier,
                                    OffsetDateTime dueDate,
                                    String courtDecisionText,
                                    String qualification,
                                    Integer deprivationDurationMonths,
                                    OffsetDateTime createdAt) {
}
