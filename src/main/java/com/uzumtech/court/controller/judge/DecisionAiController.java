package com.uzumtech.court.controller.judge;

import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.service.DecisionLlmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/court/judge/llm")
public class DecisionAiController {
    private final DecisionLlmService decisionLlmService;

    @GetMapping("/{offenseId}/suggest-penalty")
    public ResponseEntity<DecisionOutput> generateStream(@PathVariable Long offenseId) {

        DecisionOutput output = decisionLlmService.getAIDecision(offenseId);

        return ResponseEntity.ok(output);
    }
}
