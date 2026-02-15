package com.uzumtech.court.controller.admin;

import com.uzumtech.court.dto.request.LoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.service.AdminAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/admin/auth")
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody final LoginRequest request) {
        TokenResponse response = adminAuthService.login(request);

        return ResponseEntity.ok(response);
    }
}
