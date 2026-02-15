package com.uzumtech.court.dto.llm;

import java.time.OffsetDateTime;
import java.util.List;

public record DecisionPrompt(Integer codeArticleNumber,
                             String description,
                             OffsetDateTime offenseDateTime,
                             List<OffenseDto> previousOffenses) {
}
