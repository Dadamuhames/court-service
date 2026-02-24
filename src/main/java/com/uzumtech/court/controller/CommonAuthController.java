package com.uzumtech.court.controller;

import com.uzumtech.court.dto.request.RefreshRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court")
public class CommonAuthController {
    private final TokenService tokenService;

    @PostMapping({"/judge/auth/refresh", "/admin/auth/refresh", "/external-service/auth/refresh"})
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        TokenResponse response = tokenService.refreshToken(request);

        return ResponseEntity.ok(response);
    }
}
