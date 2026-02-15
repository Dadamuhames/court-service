package com.uzumtech.court.dto.request;

import com.uzumtech.court.constant.enums.PenaltyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyRequest(@NotNull(message = "offenseId required") Long offenseId,
                             @NotNull(message = "type required") PenaltyType type,
                             @NotNull(message = "bhmMultiplier required") BigDecimal bhmMultiplier,
                             @NotNull(message = "dueDate required") OffsetDateTime dueDate,
                             @NotBlank(message = "courtDecisionText required") String courtDecisionText,
                             @NotNull(message = "deprivationDurationMonths required") Integer deprivationDurationMonths) {}
