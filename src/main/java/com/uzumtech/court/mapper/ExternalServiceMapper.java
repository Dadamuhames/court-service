package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyWebhookRequest;
import com.uzumtech.court.dto.request.admin.ExternalServiceCreateRequest;
import com.uzumtech.court.dto.response.admin.ExternalServiceAdminResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExternalServiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "passwordEncrypted")
    @Mapping(target = "webhookSecret", source = "webhookSecretEncrypted")
    ExternalServiceEntity requestToEntity(ExternalServiceCreateRequest request, String passwordEncrypted, String webhookSecretEncrypted);

    ExternalServiceAdminResponse entityToResponse(ExternalServiceEntity entity);

    PenaltyWebhookRequest webhookEventToRequest(PenaltyWebhookEvent event);
}
