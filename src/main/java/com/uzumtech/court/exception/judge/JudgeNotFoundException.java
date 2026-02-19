package com.uzumtech.court.exception.judge;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class JudgeNotFoundException extends ApplicationException {
    public JudgeNotFoundException(ErrorCode error) {
        super(error);
    }
}
