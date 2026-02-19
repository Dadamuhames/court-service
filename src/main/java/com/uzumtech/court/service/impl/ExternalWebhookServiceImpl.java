package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.adapter.WebhookAdapter;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.request.PenaltyWebhookDto;
import com.uzumtech.court.dto.request.PenaltyWebhookRequest;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import com.uzumtech.court.exception.ExternalServiceLoginException;
import com.uzumtech.court.mapper.ExternalServiceMapper;
import com.uzumtech.court.repository.ExternalServiceRepository;
import com.uzumtech.court.repository.PenaltyRepository;
import com.uzumtech.court.service.ExternalWebhookService;
import com.uzumtech.court.service.PenaltyHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

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
