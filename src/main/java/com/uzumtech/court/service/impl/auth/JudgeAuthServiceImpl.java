package com.uzumtech.court.service.impl.auth;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.request.JudgeLoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.exception.user.LoginNotFoundException;
import com.uzumtech.court.exception.user.PasswordInvalidException;
import com.uzumtech.court.repository.JudgeRepository;
import com.uzumtech.court.service.JudgeAuthService;
import com.uzumtech.court.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JudgeAuthServiceImpl implements JudgeAuthService {
    private final JudgeRepository judgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public TokenResponse login(JudgeLoginRequest request) {
        JudgeEntity judgeEntity = judgeRepository.findByEmail(request.email()).orElseThrow(() -> new LoginNotFoundException(ErrorCode.LOGIN_INVALID_CODE));

        if (!passwordEncoder.matches(request.password(), judgeEntity.getPassword())) {
            throw new PasswordInvalidException(ErrorCode.PASSWORD_INVALID_CODE);
        }

        return tokenService.createPair(judgeEntity);
    }
}
