package com.uzumtech.court.exception.offense;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.exception.ApplicationException;

public class OffenceNotFoundOrStatusInvalidException extends ApplicationException {
    public OffenceNotFoundOrStatusInvalidException(ErrorCode error) {
        super(error);
    }
}
