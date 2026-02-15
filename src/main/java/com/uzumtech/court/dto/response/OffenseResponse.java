package com.uzumtech.court.dto.response;

import java.time.OffsetDateTime;

public record OffenseResponse(Long id, Long externalId, String courtCaseNumber, OffsetDateTime createdAt) {}
