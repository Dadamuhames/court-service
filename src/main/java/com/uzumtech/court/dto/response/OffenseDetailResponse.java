package com.uzumtech.court.dto.response;

import java.time.OffsetDateTime;

public record OffenseDetailResponse(Long id,
                                    Long externalId,
                                    String courtCaseNumber,
                                    String codeArticleReference,
                                    String description,
                                    String offenseLocation,
                                    String offenderExplanation,
                                    OffsetDateTime offenseDateTime,
                                    PenaltyDetailResponse penalty,
                                    OffsetDateTime createdAt) {
}
