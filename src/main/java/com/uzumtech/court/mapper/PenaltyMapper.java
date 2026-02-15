package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PenaltyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "judge", source = "judge")
    @Mapping(target = "offense", source = "offense")
    @Mapping(target = "bhmAmountAtTime", source = "currentBhmAmount")
    PenaltyEntity requestToEntity(PenaltyRequest request, JudgeEntity judge, OffenseEntity offense, Long currentBhmAmount);


    @Mapping(target = "externalOffenseId", source = "entity.offense.id")
    @Mapping(target = "externalServiceId", source = "entity.offense.externalService.id")
    PenaltyWebhookEvent entityToWebhookEvent(PenaltyEntity entity);

    PenaltyResponse entityToResponse(PenaltyEntity entity);
}
