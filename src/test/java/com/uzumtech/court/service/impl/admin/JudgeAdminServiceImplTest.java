package com.uzumtech.court.service.impl.admin;

import com.uzumtech.court.dto.request.admin.JudgeRequest;
import com.uzumtech.court.dto.response.admin.JudgeResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.exception.JudgeEmailExistsException;
import com.uzumtech.court.mapper.JudgeMapper;
import com.uzumtech.court.repository.JudgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JudgeAdminServiceImplTest {

    @Mock
    private JudgeRepository judgeRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JudgeMapper judgeMapper;

    @InjectMocks
    private JudgeAdminServiceImpl judgeAdminService;

    private JudgeRequest sharedRequest;
    private String encodedPassword;

    @BeforeEach
    void setUp() {
        encodedPassword = "encoded_hash_123";
        sharedRequest = new JudgeRequest(
            "judge@court.gov",
            "John Doe",
            "123123",
            "123123"
        );
    }

    @Test
    void list_ShouldReturnPaginatedResponses() {
        Pageable pageable = PageRequest.of(0, 10);

        JudgeEntity entity = new JudgeEntity();
        Page<JudgeEntity> entityPage = new PageImpl<>(List.of(entity));
        JudgeResponse response = new JudgeResponse(1L, "judge@court.gov", "John Doe", OffsetDateTime.now());

        when(judgeRepository.findAll(pageable)).thenReturn(entityPage);
        when(judgeMapper.entityToResponse(entity)).thenReturn(response);

        Page<JudgeResponse> result = judgeAdminService.list(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(response, result.getContent().getFirst());
        verify(judgeRepository).findAll(pageable);
    }


    @Test
    void create_ShouldSaveEncodedJudge_WhenEmailIsUnique() {
        JudgeEntity entity = new JudgeEntity();
        JudgeResponse expectedResponse = new JudgeResponse(1L, "judge@court.gov", "John Doe", OffsetDateTime.now());

        when(judgeRepository.existsByEmail(sharedRequest.email())).thenReturn(false);
        when(passwordEncoder.encode(sharedRequest.password())).thenReturn(encodedPassword);
        when(judgeMapper.requestToEntity(sharedRequest, encodedPassword)).thenReturn(entity);
        when(judgeRepository.save(entity)).thenReturn(entity);
        when(judgeMapper.entityToResponse(entity)).thenReturn(expectedResponse);

        JudgeResponse result = judgeAdminService.create(sharedRequest);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
        verify(passwordEncoder).encode(anyString());
        verify(judgeRepository).save(entity);
    }

    @Test
    void create_ShouldThrowException_WhenEmailAlreadyExists() {
        when(judgeRepository.existsByEmail(sharedRequest.email())).thenReturn(true);

        assertThrows(JudgeEmailExistsException.class, () ->
            judgeAdminService.create(sharedRequest)
        );

        verify(passwordEncoder, never()).encode(any());
        verify(judgeRepository, never()).save(any());
    }
}