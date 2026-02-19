package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class NotificationCallbackRegisteredForRequestException extends ApplicationException {
    public NotificationCallbackRegisteredForRequestException(ErrorCode error) {
        super(error);
    }
}
