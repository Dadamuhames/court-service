package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.llm.DecisionPrompt;
import com.uzumtech.court.dto.llm.OffenseDto;
import com.uzumtech.court.dto.llm.PenaltyDto;
import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import com.uzumtech.court.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OffenseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "courtCaseNumber", source = "courtCaseNumber")
    @Mapping(target = "externalId", source = "request.legalOffenseId")
    OffenseEntity requestToEntity(OffenseRegistrationRequest request, UserEntity user, String courtCaseNumber);

    OffenseResponse entityToResponse(OffenseEntity entity);

    @Mapping(target = "previousOffenses", source = "prevOffenses")
    DecisionPrompt mapDecisionPrompt(OffenseEntity offense, List<OffenseDto> prevOffenses);

    @Mapping(target = "penalty", expression = "java(penaltyToDto(offense.getPenalty()))")
    OffenseDto entityToDto(OffenseEntity offense);

    PenaltyDto penaltyToDto(PenaltyEntity penalty);
}
