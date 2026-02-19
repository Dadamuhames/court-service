package com.uzumtech.court.dto.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AiDecisionEvent(@NotNull Long offenseId, @NotBlank String judgeEmail) {

    public static AiDecisionEvent of(Long offenseId, String judgeEmail) {
        return new AiDecisionEvent(offenseId, judgeEmail);
    }
}
