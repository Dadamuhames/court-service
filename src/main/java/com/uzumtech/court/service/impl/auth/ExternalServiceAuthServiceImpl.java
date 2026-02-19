package com.uzumtech.court.service.impl.auth;


import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.request.LoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.exception.user.LoginNotFoundException;
import com.uzumtech.court.exception.user.PasswordInvalidException;
import com.uzumtech.court.repository.ExternalServiceRepository;
import com.uzumtech.court.service.TokenService;
import com.uzumtech.court.service.ExternalServiceAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExternalServiceAuthServiceImpl implements ExternalServiceAuth {
    private final ExternalServiceRepository externalServiceRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public TokenResponse login(final LoginRequest request) {
        ExternalServiceEntity penaltyService = externalServiceRepository.findByLogin(request.login()).orElseThrow(
            () -> new LoginNotFoundException(ErrorCode.LOGIN_INVALID_CODE)
        );

        if (!passwordEncoder.matches(request.password(), penaltyService.getPassword())) {
            throw new PasswordInvalidException(ErrorCode.PASSWORD_INVALID_CODE);
        }

        return tokenService.createPair(penaltyService);
    }
}
