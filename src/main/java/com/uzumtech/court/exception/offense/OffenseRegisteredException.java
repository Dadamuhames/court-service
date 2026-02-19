package com.uzumtech.court.exception.offense;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class OffenseRegisteredException extends ApplicationException {
    public OffenseRegisteredException(ErrorCode error) {
        super(error);
    }
}
