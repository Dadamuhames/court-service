package com.uzumtech.court.dto.request.notification;

import com.uzumtech.court.constant.enums.NotificationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationCallbackRequest(@NotNull Integer code, @NotBlank String message, @Valid @NotNull WebhookContent content) {
    public record WebhookContent(@NotNull Long notificationId, @NotNull NotificationStatus status) {}
}
