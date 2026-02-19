package com.uzumtech.court.exception.otp;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class OtpInvalidException extends ApplicationException {
    public OtpInvalidException(ErrorCode error) {
        super(error);
    }
}
