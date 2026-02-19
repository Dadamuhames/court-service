package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.llm.DecisionPrompt;
import com.uzumtech.court.dto.llm.OffenseDto;
import com.uzumtech.court.dto.llm.PenaltyDto;
import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseDetailResponse;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.dto.response.PenaltyDetailResponse;
import com.uzumtech.court.entity.JudgeEntity;
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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "judge", source = "judge")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "courtCaseNumber", source = "courtCaseNumber")
    @Mapping(target = "externalId", source = "request.legalOffenseId")
    OffenseEntity requestToEntity(OffenseRegistrationRequest request, UserEntity user, JudgeEntity judge, String courtCaseNumber);

    OffenseResponse entityToResponse(OffenseEntity entity);

    @Mapping(target = "previousOffenses", source = "prevOffenses")
    DecisionPrompt mapDecisionPrompt(OffenseEntity offense, List<OffenseDto> prevOffenses);

    @Mapping(target = "penalty", expression = "java(penaltyToDto(offense.getPenalty()))")
    OffenseDto entityToDto(OffenseEntity offense);

    PenaltyDto penaltyToDto(PenaltyEntity penalty);

    @Mapping(target = "id", source = "offense.id")
    @Mapping(target = "createdAt", source = "offense.createdAt")
    @Mapping(target = "penalty", source = "penalty")
    OffenseDetailResponse entityToDetailResponse(OffenseEntity offense, PenaltyDetailResponse penalty);
}
