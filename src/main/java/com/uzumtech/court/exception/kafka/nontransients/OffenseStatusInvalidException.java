package com.uzumtech.court.exception.kafka.nontransients;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;

public class OffenseStatusInvalidException extends NonTransientException {
    public OffenseStatusInvalidException(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION);
    }
}
