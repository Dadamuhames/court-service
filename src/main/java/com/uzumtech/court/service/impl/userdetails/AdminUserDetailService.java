package com.uzumtech.court.service.impl.userdetails;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.exception.ApplicationException;
import com.uzumtech.court.exception.user.UserNotFoundException;
import com.uzumtech.court.repository.AdminRepository;
import com.uzumtech.court.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserDetailService implements CustomUserDetailsService {
    private final AdminRepository adminRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws ApplicationException {
        return adminRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException(ErrorCode.ADMIN_AUTH_INVALID_CODE));
    }

    @Override
    public Role getSupportedRole() {
        return Role.ADMIN;
    }
}
