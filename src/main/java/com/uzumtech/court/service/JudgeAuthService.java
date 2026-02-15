package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.JudgeLoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;

public interface JudgeAuthService {

    TokenResponse login(JudgeLoginRequest request);
}
