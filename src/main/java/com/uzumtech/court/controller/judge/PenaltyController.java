package com.uzumtech.court.controller.judge;


import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.service.PenaltyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/judge/penalties")
public class PenaltyController {
    private final PenaltyService penaltyService;

    @PostMapping
    public ResponseEntity<PenaltyResponse> ruleOutPenalty(@AuthenticationPrincipal final JudgeEntity judge, @Valid @RequestBody final PenaltyRequest request) {

        PenaltyResponse response = penaltyService.ruleOutPenalty(request, judge);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
