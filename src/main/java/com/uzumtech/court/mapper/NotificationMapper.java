package com.uzumtech.court.mapper;

import com.uzumtech.court.dto.request.notification.NotificationCallbackRequest;
import com.uzumtech.court.entity.notification.NotificationCallbackEntity;
import com.uzumtech.court.entity.notification.NotificationRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NotificationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "notificationRequest", source = "notificationRequest")
    @Mapping(target = "status", source = "request.content.status")
    NotificationCallbackEntity requestToEntity(NotificationCallbackRequest request, NotificationRequestEntity notificationRequest);

}
