package com.uzumtech.court.controller.judge;

import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.service.DecisionLlmService;
import com.uzumtech.court.service.JudgeOffenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/judge/llm")
public class DecisionAiController {
    private final JudgeOffenseService judgeOffenseService;

    @GetMapping("/{offenseId}/suggest-penalty")
    public ResponseEntity<Void> generateStream(@PathVariable Long offenseId, @AuthenticationPrincipal JudgeEntity judge) {

        judgeOffenseService.sendForAiDecisionProcessing(offenseId, judge);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
