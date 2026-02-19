package com.uzumtech.court.exception.judge;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;
import com.uzumtech.court.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class JudgeAccessDeniedException extends ApplicationException {
    public JudgeAccessDeniedException(ErrorCode error) {
        super(error.getCode(), error.getMessage(), ErrorType.VALIDATION, HttpStatus.FORBIDDEN);
    }
}
