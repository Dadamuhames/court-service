package com.uzumtech.court.exception.judge;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class JudgeEmailExistsException extends ApplicationException {

    public JudgeEmailExistsException(ErrorCode error) {
        super(error);
    }
}
