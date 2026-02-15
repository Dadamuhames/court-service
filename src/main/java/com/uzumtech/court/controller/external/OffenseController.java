package com.uzumtech.court.controller.external;

import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.service.OffenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/external-service/legal-offenses")
public class OffenseController {
    private final OffenseService offenseService;

    @PostMapping("/registration")
    public ResponseEntity<OffenseResponse> register(@AuthenticationPrincipal final ExternalServiceEntity externalService, @Valid @RequestBody OffenseRegistrationRequest request) {
        OffenseResponse response = offenseService.register(externalService, request);

        return ResponseEntity.ok(response);
    }
}
