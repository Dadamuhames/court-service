package com.uzumtech.court.dto.request.penalty;

import com.uzumtech.court.constant.enums.PenaltyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PenaltyUpdateRequest(@NotNull(message = "type required") PenaltyType type,
                                   @NotNull(message = "bhmMultiplier required") BigDecimal bhmMultiplier,
                                   @NotBlank(message = "qualification required") String qualification,
                                   @NotNull(message = "dueDate required") OffsetDateTime dueDate,
                                   @NotBlank(message = "courtDecisionText required") String courtDecisionText,
                                   @NotNull(message = "deprivationDurationMonths required") Integer deprivationDurationMonths) {
}
