package com.uzumtech.court.dto.request;

public record PenaltyWebhookDto(String webhookUrl, String webhookSecret, PenaltyWebhookRequest request) {
}
