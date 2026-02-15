package com.uzumtech.court.service;

import com.uzumtech.court.entity.RefreshTokenEntity;
import com.uzumtech.court.entity.base.CustomUserDetails;

public interface RefreshTokenService {
    RefreshTokenEntity createRefreshToken(final CustomUserDetails userDetails);

    RefreshTokenEntity findByToken(final String token);

    CustomUserDetails getUserDetails(final RefreshTokenEntity refreshToken);

    void verifyExpiration(final RefreshTokenEntity token);

    void expireToken(RefreshTokenEntity token);
}
