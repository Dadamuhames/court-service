package com.uzumtech.court.service.impl.auth;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.request.LoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.entity.AdminEntity;
import com.uzumtech.court.exception.user.LoginNotFoundException;
import com.uzumtech.court.exception.user.PasswordInvalidException;
import com.uzumtech.court.repository.AdminRepository;
import com.uzumtech.court.service.AdminAuthService;
import com.uzumtech.court.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthServiceImpl implements AdminAuthService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Transactional
    public TokenResponse login(final LoginRequest request) {
        AdminEntity admin = adminRepository.findByLogin(request.login()).orElseThrow(() -> new LoginNotFoundException(ErrorCode.LOGIN_INVALID_CODE));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new PasswordInvalidException(ErrorCode.PASSWORD_INVALID_CODE);
        }

        return tokenService.createPair(admin);
    }
}
