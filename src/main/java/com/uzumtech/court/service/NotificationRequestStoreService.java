package com.uzumtech.court.service;

import com.uzumtech.court.constant.enums.NotificationRequestStatus;
import com.uzumtech.court.dto.event.NotificationEvent;

import java.util.UUID;

public interface NotificationRequestStoreService {

    int claimForProcessing(final UUID requestId);

    void createNotificationRequest(final NotificationEvent event);

    void markAsDelivered(final UUID requestId, final Long notificationServiceId);

    void changeStatus(final UUID requestId, final NotificationRequestStatus status);
}
