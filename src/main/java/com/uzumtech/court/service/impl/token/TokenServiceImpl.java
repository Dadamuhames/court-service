package com.uzumtech.court.service.impl.token;

import com.uzumtech.court.dto.request.RefreshRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.entity.RefreshTokenEntity;
import com.uzumtech.court.entity.base.CustomUserDetails;
import com.uzumtech.court.service.JwtService;
import com.uzumtech.court.service.RefreshTokenService;
import com.uzumtech.court.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public TokenResponse createPair(final CustomUserDetails user) {
        String accessToken = jwtService.generateToken(user);

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);

        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public TokenResponse refreshToken(final RefreshRequest request) {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(request.refreshToken());

        refreshTokenService.verifyExpiration(refreshToken);

        CustomUserDetails user = refreshTokenService.getUserDetails(refreshToken);

        // delete old token
        refreshTokenService.expireToken(refreshToken);

        return createPair(user);
    }
}
