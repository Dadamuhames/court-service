package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.admin.JudgeRequest;
import com.uzumtech.court.dto.response.admin.JudgeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JudgeAdminService {

    Page<JudgeResponse> list(final Pageable pageable);

    JudgeResponse create(final JudgeRequest request);
}
