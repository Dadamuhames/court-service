package com.uzumtech.court.controller.judge;

import com.uzumtech.court.dto.request.JudgeLoginRequest;
import com.uzumtech.court.dto.response.TokenResponse;
import com.uzumtech.court.service.JudgeAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/judge/auth")
public class JudgeAuthController {
    private final JudgeAuthService judgeAuthService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody final JudgeLoginRequest request) {
        TokenResponse response = judgeAuthService.login(request);

        return ResponseEntity.ok(response);
    }
}
