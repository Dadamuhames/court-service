package com.uzumtech.court.service.impl;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyWebhookRequest;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.exception.ExternalServiceLoginException;
import com.uzumtech.court.mapper.ExternalServiceMapper;
import com.uzumtech.court.repository.ExternalServiceRepository;
import com.uzumtech.court.service.ExternalWebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ExternalWebhookServiceImpl implements ExternalWebhookService {
    private final ExternalServiceRepository externalServiceRepository;
    private final ExternalServiceMapper externalServiceMapper;
    private final RestClient restClient;


    @Transactional(readOnly = true)
    public void sendToWebhook(final PenaltyWebhookEvent webhookEvent) {
        ExternalServiceEntity externalService = externalServiceRepository.findById(webhookEvent.externalServiceId()).orElseThrow(() -> new ExternalServiceLoginException(ErrorCode.EXTERNAL_SERVICE_ID_INVALID));

        PenaltyWebhookRequest request = externalServiceMapper.webhookEventToRequest(webhookEvent);

        restClient.post().uri(externalService.getWebhookUrl()).header("X-Court-Secret", externalService.getWebhookSecret()).body(request);
    }

}
