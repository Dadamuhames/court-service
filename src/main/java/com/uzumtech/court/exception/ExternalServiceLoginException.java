package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class ExternalServiceLoginException extends ApplicationException {
    public ExternalServiceLoginException(ErrorCode error) {
        super(error);
    }
}
