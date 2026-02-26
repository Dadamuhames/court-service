package com.uzumtech.court.dto.request.penalty;

import com.uzumtech.court.constant.enums.PenaltyType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyWebhookRequest(Long id, Long externalOffenseId, PenaltyType type, Long bhmAmountAtTime,
                                    BigDecimal bhmMultiplier, OffsetDateTime dueDate, String qualification,
                                    String courtDecisionText,
                                    Integer deprivationDurationMonths, OffsetDateTime createdAt) {}
