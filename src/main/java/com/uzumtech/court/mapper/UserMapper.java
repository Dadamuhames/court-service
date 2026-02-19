package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.response.GcpResponse;
import com.uzumtech.court.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fullName", expression = "java(concatName(gcpResponse))")
    @Mapping(target = "pinfl", source = "gcpResponse.personalIdentificationNumber")
    UserEntity gcpResponseToUserEntity(GcpResponse gcpResponse);

    default String concatName(GcpResponse gcpResponse) {
        return String.format("%s %s", gcpResponse.name(), gcpResponse.surname());
    }
}
