package com.uzumtech.court.dto.request;

import com.uzumtech.court.constant.enums.PenaltyType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyWebhookRequest(Long id, Long externalOffenseId, PenaltyType type, Long bhmAmountAtTime,
                                    BigDecimal bhmMultiplier, OffsetDateTime dueDate, String courtDecisionText,
                                    Integer deprivationDurationMonths) {}
