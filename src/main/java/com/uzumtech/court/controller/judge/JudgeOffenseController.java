package com.uzumtech.court.controller.judge;

import com.uzumtech.court.dto.response.OffenseDetailResponse;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.service.JudgeOffenseService;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/judge/offenses")
public class JudgeOffenseController {

    private final JudgeOffenseService judgeOffenseService;

    @GetMapping
    public ResponseEntity<Page<OffenseResponse>> list(@AuthenticationPrincipal JudgeEntity judge, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") @Max(50) Integer pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<OffenseResponse> offenses = judgeOffenseService.list(judge, pageable);

        return ResponseEntity.ok(offenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffenseDetailResponse> one(@PathVariable Long id, @AuthenticationPrincipal JudgeEntity judge) {

        OffenseDetailResponse response = judgeOffenseService.getOne(id, judge);

        return ResponseEntity.ok(response);
    }

}
