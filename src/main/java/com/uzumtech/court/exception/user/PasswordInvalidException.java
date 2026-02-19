package com.uzumtech.court.exception.user;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;
import com.uzumtech.court.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PasswordInvalidException extends ApplicationException {
    public PasswordInvalidException(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION, HttpStatus.BAD_REQUEST);
    }
}
