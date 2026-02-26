package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.adapter.WebhookAdapter;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.penalty.PenaltyWebhookDto;
import com.uzumtech.court.service.ExternalWebhookService;
import com.uzumtech.court.service.PenaltyHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalWebhookServiceImpl implements ExternalWebhookService {
    private final PenaltyHelperService penaltyHelperService;
    private final WebhookAdapter webhookAdapter;


    public void sendToWebhook(final PenaltyWebhookEvent webhookEvent) {

        PenaltyWebhookDto webhookDto = penaltyHelperService.getWebhookDto(webhookEvent);

        webhookAdapter.sendWebhook(webhookDto);
    }

}
