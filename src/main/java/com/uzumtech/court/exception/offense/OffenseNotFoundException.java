package com.uzumtech.court.exception.offense;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class OffenseNotFoundException extends ApplicationException {
    public OffenseNotFoundException(ErrorCode error) {
        super(error);
    }
}
