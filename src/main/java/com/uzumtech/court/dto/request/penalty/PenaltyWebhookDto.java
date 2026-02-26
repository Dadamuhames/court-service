package com.uzumtech.court.dto.request.penalty;

public record PenaltyWebhookDto(String webhookUrl, String webhookSecret, PenaltyWebhookRequest request) {
}
