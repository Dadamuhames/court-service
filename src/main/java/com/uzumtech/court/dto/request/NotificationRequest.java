package com.uzumtech.court.dto.request;

import com.uzumtech.court.constant.enums.NotificationType;

public record NotificationRequest(NotificationReceiver receiver, NotificationType type, String text) {
    public record NotificationReceiver(String phone, String email, String firebaseToken) {}

    public static NotificationRequest sms(String phone, String text) {
        var receiver = new NotificationReceiver(phone, null, null);

        return new NotificationRequest(receiver, NotificationType.SMS, text);
    }

    public static NotificationRequest email(String email, String text) {
        var receiver = new NotificationReceiver(null, email, null);

        return new NotificationRequest(receiver, NotificationType.EMAIL, text);
    }

    public static NotificationRequest push(String firebaseToken, String text) {
        var receiver = new NotificationReceiver(null, null, firebaseToken);

        return new NotificationRequest(receiver, NotificationType.PUSH, text);
    }

    public static NotificationRequest of(String receiver, String text, NotificationType type) {
        return switch (type) {
            case SMS -> NotificationRequest.sms(receiver, text);
            case EMAIL -> NotificationRequest.email(receiver, text);
            case PUSH -> NotificationRequest.push(receiver, text);
        };
    }
}
