package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class OtpCheckLockedException extends ApplicationException {
    public OtpCheckLockedException(ErrorCode error) {
        super(error);
    }
}
