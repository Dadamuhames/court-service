package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class OffenseRegisteredException extends ApplicationException {
    public OffenseRegisteredException(ErrorCode error) {
        super(error);
    }
}
