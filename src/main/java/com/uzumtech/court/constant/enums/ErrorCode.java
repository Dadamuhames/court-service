package com.uzumtech.court.constant.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INTERNAL_SERVICE_ERROR_CODE(10001, "System not available"),
    EXTERNAL_SERVICE_FAILED_ERROR_CODE(10002, "External service not available"),
    HANDLER_NOT_FOUND_ERROR_CODE(10003, "Handler not found"),
    JSON_NOT_VALID_ERROR_CODE(10004, "Json not valid"),
    VALIDATION_ERROR_CODE(10005, "Validation error"),
    INVALID_REQUEST_PARAM_ERROR_CODE(10006, "Invalid request param"),
    INTERNAL_TIMEOUT_ERROR_CODE(10007, "Internal timeout"),
    METHOD_NOT_SUPPORTED_ERROR_CODE(10008, "Method not supported"),
    MISSING_REQUEST_HEADER_ERROR_CODE(10009, "Missing request header"),

    ROLE_NOT_SUPPORTED_CODE(10010, "Provided Role not supported by the system"),

    // 10400-10500 AUTH ERRORS
    INSPECTOR_AUTH_INVALID_CODE(10400, "Inspector authentication invalid"),
    ADMIN_AUTH_INVALID_CODE(10410, "ADMIN authentication invalid"),
    JWT_INVALID_CODE(10430, "JWT invalid"),
    REFRESH_TOKEN_INVALID_CODE(10440, "Refresh token invalid"),
    LOGIN_INVALID_CODE(10450, "Login invalid"),
    PASSWORD_INVALID_CODE(10460, "Password invalid"),

    HTTP_CLIENT_ERROR_CODE(14000, "Http Client error code"),
    HTTP_SERVER_ERROR_CODE(15000, "Http Server error code"),
    OFFENSE_NOT_FOUND_CODE(10500, "Offense not found"),
    BHM_NOT_SPECIFIED_CODE(10510, "BHM amount not specified"),
    EXTERNAL_SERVICE_LOGIN(10520, "External service login exists"),
    EXTERNAL_SERVICE_ID_INVALID(10530, "External service ID invalid"),
    EXTERNAL_WEBHOOK_REQUEST_INVALID_CODE(10540, "External webhook request invalid"),
    OFFENSE_REGISTERED_CODE(10550, "Offense already registered"),

    OFFENSE_CANT_BE_SENT_TO_AI_DECISION_PROCESSING(10560, "Offense Id invalid or Offense already being processed for AI Decision"),
    OFFENSE_ID_INVALID_OR_CLOSED(10570, "Offense id invalid or already closed"),

    DECISION_AI_REQUEST_INVALID_CODE(10600, "Decision AI request invalid"),
    NOTIFICATION_REQUEST_INVALID_CODE(10610, "Request to notification service invalid"),

    JUDGE_EMAIL_EXISTS_CODE(10700, "Judge email should be unique"),
    JUDGE_NOT_FOUND_CODE(10710, "Could not find a judge for the case"),
    JUDGE_ACCESS_DENIED_CODE(10720, "provided judge is not assigned to this case"),

    PENALTY_NOT_FOUND_CODE(10800, "Penalty not found"),
    PENALTY_STATUS_INVALID_CODE(10810, "Penalty is in terminal status so it cannot be confirmed or updated"),
    PENALTY_EXISTS_CODE(10820, "Penalty for the offense already exists"),

    NOTIFICATION_ID_INVALID_CODE(10900, "Notification id invalid");

    final int code;
    final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
