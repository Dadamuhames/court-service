package com.uzumtech.court.exception.penalty;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class PenaltyStatusInvalidException extends ApplicationException {
    public PenaltyStatusInvalidException(ErrorCode error) {
        super(error);
    }
}
