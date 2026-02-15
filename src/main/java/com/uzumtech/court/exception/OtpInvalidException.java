package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class OtpInvalidException extends ApplicationException {
    public OtpInvalidException(ErrorCode error) {
        super(error);
    }
}
