package com.uzumtech.court.exception.penalty;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;
import com.uzumtech.court.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PenaltyNotFoundException extends ApplicationException {
    public PenaltyNotFoundException(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION, HttpStatus.NOT_FOUND);
    }
}
