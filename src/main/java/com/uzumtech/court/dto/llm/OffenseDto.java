package com.uzumtech.court.dto.llm;

import java.time.OffsetDateTime;

public record OffenseDto(String codeArticleReference,
                         String description,
                         PenaltyDto penalty,
                         OffsetDateTime offenseDateTime) {
}
