package com.uzumtech.court.controller.admin;


import com.uzumtech.court.dto.request.admin.ExternalServiceCreateRequest;
import com.uzumtech.court.dto.response.admin.ExternalServiceAdminResponse;
import com.uzumtech.court.service.ExternalServiceAdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/admin/external-services")
public class ExternalServiceController {
    private final ExternalServiceAdminService externalServiceAdminService;

    @GetMapping
    public ResponseEntity<Page<ExternalServiceAdminResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") @Max(50) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<ExternalServiceAdminResponse> externalServices = externalServiceAdminService.list(pageable);

        return ResponseEntity.ok(externalServices);
    }


    @PostMapping
    public ResponseEntity<ExternalServiceAdminResponse> create(@Valid @RequestBody final ExternalServiceCreateRequest request) {
        ExternalServiceAdminResponse response = externalServiceAdminService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
