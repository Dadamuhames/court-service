package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.NotificationCallbackRequest;

public interface NotificationCallbackStoreService {

    void receiveNotificationWebhook(NotificationCallbackRequest callbackRequest);
}
