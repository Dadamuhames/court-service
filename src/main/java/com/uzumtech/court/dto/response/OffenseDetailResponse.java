package com.uzumtech.court.dto.response;

import java.time.OffsetDateTime;

public record OffenseDetailResponse(Long id,
                                    Long externalId,
                                    String courtCaseNumber,
                                    Integer codeArticleNumber,
                                    String description,
                                    String offenseLocation,
                                    String offenderExplanation,
                                    OffsetDateTime offenseDateTime,
                                    PenaltyDetailResponse penalty,
                                    OffsetDateTime createdAt) {
}
