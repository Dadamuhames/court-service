package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.LoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;

public interface ExternalServiceAuth {

    TokenResponse login(final LoginRequest request);
}
