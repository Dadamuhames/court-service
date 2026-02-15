package com.uzumtech.court.exception;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class RoleNotSupportedException extends ApplicationException {
  public RoleNotSupportedException(ErrorCode error) {
    super(error.getCode(), error.getMessage(), ErrorType.EXTERNAL, HttpStatus.BAD_REQUEST);
  }
}
