package com.uzumtech.court.service;

import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.dto.llm.DecisionPrompt;

public interface DecisionLlmService {

    DecisionOutput getAIDecision(Long offenceId);

    DecisionPrompt getDecisionPrompt(Long offenceId);

    DecisionOutput getAIDecision(DecisionPrompt prompt);
}
