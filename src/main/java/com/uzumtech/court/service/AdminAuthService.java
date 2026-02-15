package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.LoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;

public interface AdminAuthService {

    TokenResponse login(final LoginRequest request);
}
