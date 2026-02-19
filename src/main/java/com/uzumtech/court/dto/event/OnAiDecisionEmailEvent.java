package com.uzumtech.court.dto.event;

import javax.validation.constraints.NotNull;

public record OnAiDecisionEmailEvent(@NotNull String email, @NotNull Long offenseId) {
}
