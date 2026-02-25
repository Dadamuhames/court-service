package com.uzumtech.court.service.impl.token;

import com.uzumtech.court.configuration.property.JwtProperty;
import com.uzumtech.court.entity.base.CustomUserDetails;
import com.uzumtech.court.repository.RefreshTokenRepository;
import com.uzumtech.court.service.RefreshTokenService;
import com.uzumtech.court.service.impl.userdetails.UserDetailDispatcher;
import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.entity.RefreshTokenEntity;
import com.uzumtech.court.exception.user.RefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProperty jwtProperty;
    private final UserDetailDispatcher detailsServiceDispatcher;

    @Transactional
    public RefreshTokenEntity createRefreshToken(final CustomUserDetails userDetails) {

        var expiryDate = Instant.now().plusSeconds(jwtProperty.getRefreshTtlSeconds());

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
            .token(UUID.randomUUID().toString())
            .userRole(userDetails.getUserRole())
            .subject(userDetails.getUsername())
            .expiryDate(expiryDate)
            .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenEntity findByToken(final String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> new RefreshTokenException(ErrorCode.REFRESH_TOKEN_INVALID_CODE));
    }


    public CustomUserDetails getUserDetails(final RefreshTokenEntity refreshToken) {
        return (CustomUserDetails) detailsServiceDispatcher.loadUserByLoginAndRole(refreshToken.getSubject(), refreshToken.getUserRole());
    }


    public void verifyExpiration(final RefreshTokenEntity token) throws RefreshTokenException {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException(ErrorCode.REFRESH_TOKEN_INVALID_CODE);
        }
    }

    public void expireToken(RefreshTokenEntity token) {
        token.setExpiryDate(Instant.now());
        refreshTokenRepository.save(token);
    }
}
