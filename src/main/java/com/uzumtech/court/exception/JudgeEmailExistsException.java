package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;

public class JudgeEmailExistsException extends ApplicationException {

    public JudgeEmailExistsException(ErrorCode error) {
        super(error);
    }
}
