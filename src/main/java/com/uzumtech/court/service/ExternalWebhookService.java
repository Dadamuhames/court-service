package com.uzumtech.court.service;

import com.uzumtech.court.dto.event.PenaltyWebhookEvent;

public interface ExternalWebhookService {

    void sendToWebhook(final PenaltyWebhookEvent webhookEvent);
}
