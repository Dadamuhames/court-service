package com.uzumtech.court.dto.response;

public record NotificationResponse(NotificationResponseData data) {
    public record NotificationResponseData(Long notificationId) {}
}
