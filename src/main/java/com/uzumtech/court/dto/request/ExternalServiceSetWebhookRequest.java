package com.uzumtech.court.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ExternalServiceSetWebhookRequest(
    @NotBlank(message = "webhook URL required") @URL(message = "webhookUrl should be a https valid url", protocol = "https") String webhookUrl,
    @NotBlank(message = "Webhook secret required") String webhookSecret) {
}
