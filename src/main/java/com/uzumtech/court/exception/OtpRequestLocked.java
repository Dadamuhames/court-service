package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class OtpRequestLocked extends ApplicationException {
    public OtpRequestLocked(ErrorCode error) {
        super(error);
    }
}
