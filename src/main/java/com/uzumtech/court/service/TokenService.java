package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.RefreshRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.entity.base.CustomUserDetails;

public interface TokenService {
    TokenResponse createPair(final CustomUserDetails user);

    TokenResponse refreshToken(final RefreshRequest request);
}
