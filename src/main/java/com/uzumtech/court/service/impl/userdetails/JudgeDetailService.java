package com.uzumtech.court.service.impl.userdetails;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.exception.UserNotFoundException;
import com.uzumtech.court.repository.JudgeRepository;
import com.uzumtech.court.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JudgeDetailService implements CustomUserDetailsService {
    private final JudgeRepository judgeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        return judgeRepository
            .findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.INSPECTOR_AUTH_INVALID_CODE));
    }

    @Override
    public Role getSupportedRole() {
        return Role.JUDGE;
    }
}
