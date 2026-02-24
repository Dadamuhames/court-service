package com.uzumtech.court.service.impl;

import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.UserEntity;
import com.uzumtech.court.exception.offense.OffenseNotFoundException;
import com.uzumtech.court.exception.offense.OffenseRegisteredException;
import com.uzumtech.court.mapper.OffenseMapper;
import com.uzumtech.court.repository.JudgeRepository;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.service.UserRegisterService;
import com.uzumtech.court.utils.CourtCaseNumberUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OffenseServiceImplTest {

    @Mock
    private OffenseRepository offenseRepository;

    @Mock
    private UserRegisterService userRegisterService;
    @Mock
    private CourtCaseNumberUtils courtCaseNumberUtils;
    @Mock
    private OffenseMapper offenseMapper;

    @Mock
    private JudgeRepository judgeRepository;

    @InjectMocks
    private OffenseServiceImpl offenseService;

    private OffenseRegistrationRequest request;
    private ExternalServiceEntity externalService;

    @BeforeEach
    void setUp() {
        externalService = new ExternalServiceEntity();

        request = new OffenseRegistrationRequest(
            100L,
            "12345678901234",
            "Tashkent, Uzbekistan",
            "PN-999",
            "I was speeding because...",
            "Speeding violation",
            OffsetDateTime.now(),
            "123"
        );
    }


    @Test
    void findById_ShouldReturnEntity_WhenExists() throws OffenseNotFoundException {
        Long id = 1L;
        OffenseEntity entity = new OffenseEntity();

        when(offenseRepository.findById(id)).thenReturn(Optional.of(entity));

        OffenseEntity result = offenseService.findById(id);

        assertNotNull(result);
        verify(offenseRepository).findById(id);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        Long id = 1L;
        when(offenseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(OffenseNotFoundException.class, () -> offenseService.findById(id));
    }

    @Test
    void register_ShouldSaveAndReturnResponse_WhenValidRequest() {
        UserEntity mockUser = new UserEntity();
        JudgeEntity mockJudge = new JudgeEntity();

        String mockCaseNumber = "CASE-2024-001";
        OffenseEntity mockOffense = new OffenseEntity();

        OffenseResponse expectedResponse = new OffenseResponse(1L, 1L, mockCaseNumber, OffsetDateTime.now());

        when(userRegisterService.findUserByPinflOrRegister(request.offenderPinfl())).thenReturn(mockUser);
        when(courtCaseNumberUtils.generateProtocolNumber()).thenReturn(mockCaseNumber);
        when(judgeRepository.findRandomJudge()).thenReturn(Optional.of(mockJudge));
        when(offenseMapper.requestToEntity(request, mockUser, mockJudge, mockCaseNumber)).thenReturn(mockOffense);
        when(offenseRepository.save(any(OffenseEntity.class))).thenReturn(mockOffense);
        when(offenseMapper.entityToResponse(mockOffense)).thenReturn(expectedResponse);

        OffenseResponse actualResponse = offenseService.register(externalService, request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        verify(offenseRepository).save(mockOffense);
        assertEquals(externalService, mockOffense.getExternalService());
    }
}