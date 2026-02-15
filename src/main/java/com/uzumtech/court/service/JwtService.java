package com.uzumtech.court.service;

import com.uzumtech.court.constant.enums.Role;
import com.uzumtech.court.entity.base.CustomUserDetails;
import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractSubject(final String jwt);

    Role extractRole(final String jwt);

    Claims extractAllClaims(final String jwt);

    SecretKey getSignInKey();

    String generateToken(final Map<String, Object> extraClaims, final String subject);

    String generateToken(final CustomUserDetails userDetails);

    boolean isTokenExpired(final String jwt);

    Date extractExpiration(final String jwt);
}
