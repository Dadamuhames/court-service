package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.kafka.producer.PenaltyWebhookEventProducer;
import com.uzumtech.court.constant.enums.PenaltyStatus;
import com.uzumtech.court.constant.enums.PenaltyType;
import com.uzumtech.court.dto.event.PenaltyWebhookEvent;
import com.uzumtech.court.dto.request.PenaltyRequest;
import com.uzumtech.court.dto.response.PenaltyResponse;
import com.uzumtech.court.entity.BhmAmountEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.PenaltyEntity;
import com.uzumtech.court.exception.offense.OffenseNotFoundException;
import com.uzumtech.court.mapper.PenaltyMapper;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.repository.PenaltyRepository;
import com.uzumtech.court.service.BhmService;
import com.uzumtech.court.service.PenaltyHelperService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PenaltyServiceImplTest {

    @Mock
    private OffenseRepository offenseRepository;

    @Mock
    private PenaltyHelperService penaltyHelperService;

    @Mock
    private PenaltyRepository penaltyRepository;
    @Mock
    private PenaltyMapper penaltyMapper;
    @Mock
    private PenaltyWebhookEventProducer penaltyWebhookEventProducer;
    @Mock
    private BhmService bhmService;

    @InjectMocks
    private PenaltyServiceImpl penaltyService;

    private PenaltyRequest sharedRequest;
    private JudgeEntity mockJudge;
    private OffenseEntity mockOffense;
    private PenaltyWebhookEvent webhookEvent;
    private PenaltyResponse response;
    private long currentBhm;

    @BeforeEach
    void setUp() {
        currentBhm = 340000;
        mockJudge = new JudgeEntity();
        mockOffense = new OffenseEntity();

        mockJudge.setId(1L);
        mockOffense.setJudge(mockJudge);

        sharedRequest = new PenaltyRequest(
            500L,
            PenaltyType.FINE,
            "267 article",
            new BigDecimal("2.5"),
            OffsetDateTime.now().plusDays(30),
            "Decision details here",
            0
        );

        webhookEvent = new PenaltyWebhookEvent(1L);

        response = new PenaltyResponse(1L, PenaltyType.FINE, PenaltyStatus.CONFIRMED,
            340000L, new BigDecimal("2.5"), OffsetDateTime.now(), OffsetDateTime.now());
    }

    @Test
    void ruleOutPenalty_ShouldExecuteFullWorkflowSuccessfully() {
        BhmAmountEntity bhmEntity = new BhmAmountEntity();
        bhmEntity.setAmount(currentBhm);

        PenaltyEntity penalty = new PenaltyEntity();
        PenaltyEntity savedPenalty = PenaltyEntity.builder().id(1L).build();

        when(offenseRepository.findById(sharedRequest.offenseId())).thenReturn(Optional.of(mockOffense));
        when(bhmService.getCurrentAmount()).thenReturn(bhmEntity);
        when(penaltyMapper.requestToEntity(sharedRequest, mockOffense, currentBhm)).thenReturn(penalty);

        when(penaltyRepository.existsByOffenseId(sharedRequest.offenseId())).thenReturn(false);

        when(penaltyHelperService.savePenalty(penalty)).thenReturn(savedPenalty);
        when(penaltyMapper.entityToResponse(savedPenalty)).thenReturn(response);

        PenaltyResponse result = penaltyService.ruleOutPenalty(sharedRequest, mockJudge);

        assertNotNull(result);
        assertEquals(response, result);
        verify(penaltyHelperService).savePenalty(penalty);
        verify(penaltyWebhookEventProducer).publishWebhookEvent(webhookEvent);
    }

    @Test
    void ruleOutPenalty_ShouldThrowException_WhenOffenseIsMissing() {
        when(offenseRepository.findById(sharedRequest.offenseId())).thenReturn(Optional.empty());

        assertThrows(OffenseNotFoundException.class, () ->
            penaltyService.ruleOutPenalty(sharedRequest, mockJudge)
        );

        verifyNoInteractions(bhmService, penaltyWebhookEventProducer);
    }
}