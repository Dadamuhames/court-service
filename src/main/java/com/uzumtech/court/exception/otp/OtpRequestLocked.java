package com.uzumtech.court.exception.otp;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class OtpRequestLocked extends ApplicationException {
    public OtpRequestLocked(ErrorCode error) {
        super(error);
    }
}
