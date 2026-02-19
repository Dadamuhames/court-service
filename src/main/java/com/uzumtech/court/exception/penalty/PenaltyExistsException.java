package com.uzumtech.court.exception.penalty;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class PenaltyExistsException extends ApplicationException {
    public PenaltyExistsException(ErrorCode error) {
        super(error);
    }
}
