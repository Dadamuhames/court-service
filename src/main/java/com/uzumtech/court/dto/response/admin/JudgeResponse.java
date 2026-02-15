package com.uzumtech.court.dto.response.admin;

import java.time.OffsetDateTime;

public record JudgeResponse(Long id, String email, String fullName, OffsetDateTime createdAt) {
}
