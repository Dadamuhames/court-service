package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class OffenseNotFoundException extends ApplicationException {
    public OffenseNotFoundException(ErrorCode error) {
        super(error);
    }
}
