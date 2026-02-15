package com.uzumtech.court.dto.event;

import com.uzumtech.court.constant.enums.PenaltyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyWebhookEvent(@NotNull Long id, @NotNull Long externalOffenseId, @NotNull Long externalServiceId,
                                  @NotNull PenaltyType type, @NotNull Long bhmAmountAtTime,
                                  @NotNull BigDecimal bhmMultiplier, @NotNull OffsetDateTime dueDate,
                                  @NotBlank String courtDecisionText, @NotNull Integer deprivationDurationMonths) {}
