package com.uzumtech.court.service;

import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.exception.ApplicationException;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsername(final String login) throws ApplicationException;

    Role getSupportedRole();
}
