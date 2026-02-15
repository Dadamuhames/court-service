package com.uzumtech.court.dto.response.admin;

import java.time.OffsetDateTime;

public record ExternalServiceAdminResponse(Long id, String login, OffsetDateTime createdAt) {
}
