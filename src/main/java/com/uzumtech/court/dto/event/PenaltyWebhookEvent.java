package com.uzumtech.court.dto.event;

import jakarta.validation.constraints.NotNull;

public record PenaltyWebhookEvent(@NotNull Long penaltyId) {}

