package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.notification.NotificationCallbackRequest;

public interface NotificationCallbackStoreService {

    void receiveNotificationWebhook(NotificationCallbackRequest callbackRequest);
}
