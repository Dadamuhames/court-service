package com.uzumtech.court.service.impl;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.llm.DecisionOutput;
import com.uzumtech.court.dto.llm.DecisionPrompt;
import com.uzumtech.court.dto.llm.OffenseDto;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.exception.DecisionAiException;
import com.uzumtech.court.mapper.OffenseMapper;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.service.DecisionLlmService;
import com.uzumtech.court.service.OffenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DecisionLlmServiceImpl implements DecisionLlmService {
    private final OffenseService offenseService;
    private final OffenseRepository offenseRepository;
    private final OffenseMapper offenseMapper;
    private final OpenAiChatModel chatModel;
    private final VectorStore vectorStore;
    private final ObjectMapper objectMapper;
    private final OpenAiChatOptions chatOptions;


    @Value("classpath:/ai/prompts/decision_ai_prompt.txt")
    private Resource systemPrompt;

    @Override
    public DecisionOutput getAIDecision(Long offenceId) {

        DecisionPrompt prompt = getDecisionPrompt(offenceId);

        return getAIDecision(prompt);
    }

    @Override
    @Transactional(readOnly = true)
    public DecisionPrompt getDecisionPrompt(Long offenceId) {

        OffenseEntity offense = offenseService.findById(offenceId);

        List<OffenseEntity> previousOffenses = offenseRepository.findByUserIdAndIdNot(offense.getUser().getId(), offenceId);

        List<OffenseDto> prevOffensesMapped = previousOffenses.stream().map(offenseMapper::entityToDto).toList();

        return offenseMapper.mapDecisionPrompt(offense, prevOffensesMapped);
    }

    @Override
    public DecisionOutput getAIDecision(DecisionPrompt prompt) {

        ChatResponse chatResponse = ChatClient.builder(chatModel)
            .build().prompt().options(chatOptions)
            .advisors(QuestionAnswerAdvisor.builder(vectorStore).build())
            .system(systemPrompt)
            .user(objectMapper.writeValueAsString(prompt))
            .call().chatResponse();


        if (chatResponse == null) {
            throw new DecisionAiException(ErrorCode.DECISION_AI_REQUEST_INVALID_CODE);
        }

        return objectMapper.readValue(chatResponse.getResult().getOutput().getText(), DecisionOutput.class);
    }
}
