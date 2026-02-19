package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class NotificationIdInvalidException extends ApplicationException {
    public NotificationIdInvalidException(ErrorCode error) {
        super(error);
    }
}
