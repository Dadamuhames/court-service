package com.uzumtech.court.controller.admin;

import com.uzumtech.court.dto.request.admin.JudgeRequest;
import com.uzumtech.court.dto.response.admin.JudgeResponse;
import com.uzumtech.court.service.JudgeAdminService;
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
@RequestMapping("/api/v1/court/admin/judges")
public class JudgeAdminController {
    private final JudgeAdminService judgeAdminService;

    @GetMapping
    public ResponseEntity<Page<JudgeResponse>> list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") @Max(50) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<JudgeResponse> judges = judgeAdminService.list(pageable);

        return ResponseEntity.ok(judges);
    }

    @PostMapping
    public ResponseEntity<JudgeResponse> create(@Valid @RequestBody final JudgeRequest request) {

        JudgeResponse response = judgeAdminService.create(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
