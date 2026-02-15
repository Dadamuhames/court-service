package com.uzumtech.court.dto.llm;

import java.time.OffsetDateTime;

public record OffenseDto(Integer codeArticleNumber,
                         String description,
                         PenaltyDto penalty,
                         OffsetDateTime offenseDateTime) {
}
