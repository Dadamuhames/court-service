package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class PasswordInvalidException extends ApplicationException {
    public PasswordInvalidException(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION, HttpStatus.BAD_REQUEST);
    }
}
