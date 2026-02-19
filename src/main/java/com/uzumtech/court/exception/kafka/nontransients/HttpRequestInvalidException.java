package com.uzumtech.court.exception.kafka.nontransients;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;

public class HttpRequestInvalidException extends NonTransientException {
    public HttpRequestInvalidException(ErrorCode error, Exception ex) {
        super(error.getCode(), ex.getMessage(), ErrorType.INTERNAL);
    }
}
