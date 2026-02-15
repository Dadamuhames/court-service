package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.request.admin.JudgeRequest;
import com.uzumtech.court.dto.response.admin.JudgeResponse;
import com.uzumtech.court.entity.JudgeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JudgeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "password", source = "encodedPassword")
    JudgeEntity requestToEntity(JudgeRequest request, String encodedPassword);

    JudgeResponse entityToResponse(JudgeEntity entity);
}
