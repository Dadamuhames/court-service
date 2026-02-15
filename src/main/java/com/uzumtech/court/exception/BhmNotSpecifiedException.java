package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class BhmNotSpecifiedException extends ApplicationException {

    public BhmNotSpecifiedException(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
