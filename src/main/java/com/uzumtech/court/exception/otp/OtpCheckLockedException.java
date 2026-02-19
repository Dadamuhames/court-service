package com.uzumtech.court.exception.otp;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class OtpCheckLockedException extends ApplicationException {
    public OtpCheckLockedException(ErrorCode error) {
        super(error);
    }
}
