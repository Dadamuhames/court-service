package com.uzumtech.court.service.impl.userdetails;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.exception.RoleNotSupportedException;
import com.uzumtech.court.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class    UserDetailDispatcher {
    private final Map<Role, CustomUserDetailsService> customUserDetailServices;

    public UserDetails loadUserByLoginAndRole(String login, Role role)
        throws RoleNotSupportedException {

        CustomUserDetailsService userDetailsService = customUserDetailServices.get(role);

        if (userDetailsService == null) {
            throw new RoleNotSupportedException(ErrorCode.ROLE_NOT_SUPPORTED_CODE);
        }

        return userDetailsService.loadUserByUsername(login);
    }
}
