package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.request.PenaltyUpdateRequest;
import com.uzumtech.court.dto.request.PenaltyWebhookRequest;
import com.uzumtech.court.dto.response.PenaltyDetailResponse;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PenaltyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "offense", source = "offense")
    @Mapping(target = "status", constant = "CONFIRMED")
    @Mapping(target = "bhmAmountAtTime", source = "currentBhmAmount")
    PenaltyEntity requestToEntity(PenaltyRequest request, OffenseEntity offense, Long currentBhmAmount);

    PenaltyResponse entityToResponse(PenaltyEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "offense", ignore = true)
    @Mapping(target = "bhmAmountAtTime", source = "currentBhmAmount")
    void aiDecisionToPenalty(DecisionOutput output, Long currentBhmAmount, @MappingTarget PenaltyEntity penalty);

    PenaltyDetailResponse entityToDetailResponse(PenaltyEntity penalty);

    @Mapping(target = "status", constant = "CONFIRMED")
    void updatePenalty(PenaltyUpdateRequest request, @MappingTarget PenaltyEntity entity);


    @Mapping(target = "externalOffenseId", source = "externalServiceId")
    PenaltyWebhookRequest entityToWebhookRequest(PenaltyEntity entity, Long externalServiceId);
}
