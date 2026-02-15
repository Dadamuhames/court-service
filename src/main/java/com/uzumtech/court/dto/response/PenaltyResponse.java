package com.uzumtech.court.dto.response;

import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.constant.enums.PenaltyType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyResponse(Long id, PenaltyType type, PenaltyStatus status, Long bhmAmountAtTime,
                              BigDecimal bhmMultiplier, OffsetDateTime dueDate, OffsetDateTime createdAt) {}
