package com.uzumtech.court.exception.kafka.nontransients;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;

public class ExternalServiceNotFound extends NonTransientException {
    public ExternalServiceNotFound(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.INTERNAL, null);
    }
}
