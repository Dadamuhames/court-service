package com.uzumtech.court.service.impl.userdetails;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.exception.UserNotFoundException;
import com.uzumtech.court.repository.ExternalServiceRepository;
import com.uzumtech.court.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalServiceDetailService implements CustomUserDetailsService {
    private final ExternalServiceRepository externalServiceRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UserNotFoundException {
        return externalServiceRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(ErrorCode.ADMIN_AUTH_INVALID_CODE));
    }

    @Override
    public Role getSupportedRole() {
        return Role.SERVICE;
    }
}
