package com.uzumtech.court.mapper;


import com.uzumtech.court.dto.response.GcpResponse;
import com.uzumtech.court.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    UserEntity gcpResponseToUserEntity(GcpResponse gcpResponse);
}
