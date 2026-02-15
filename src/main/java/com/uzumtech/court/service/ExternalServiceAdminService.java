package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.admin.ExternalServiceCreateRequest;
import com.uzumtech.court.dto.response.admin.ExternalServiceAdminResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ExternalServiceAdminService {

    Page<ExternalServiceAdminResponse> list(final Pageable pageable);

    ExternalServiceAdminResponse create(@Valid @RequestBody final ExternalServiceCreateRequest request);
}
