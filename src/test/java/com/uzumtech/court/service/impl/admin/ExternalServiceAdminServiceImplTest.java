package com.uzumtech.court.service.impl.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.OffsetDateTime;
import java.util.List;

import com.uzumtech.court.dto.request.admin.ExternalServiceCreateRequest;
import com.uzumtech.court.dto.response.admin.ExternalServiceAdminResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.exception.ExternalServiceLoginException;
import com.uzumtech.court.mapper.ExternalServiceMapper;
import com.uzumtech.court.repository.ExternalServiceRepository;
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

@ExtendWith(MockitoExtension.class)
class ExternalServiceAdminServiceImplTest {
    @Mock
    private ExternalServiceRepository externalServiceRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ExternalServiceMapper externalServiceMapper;

    @InjectMocks
    private ExternalServiceAdminServiceImpl externalServiceAdminService;

    private ExternalServiceCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new ExternalServiceCreateRequest(
            "fines",
            "123123",
            "123123",
            "https://api.traffic-police.gov/callback",
            "secret"
        );
    }

    @Test
    void list_ShouldReturnMappedPage() {
        Pageable pageable = PageRequest.of(0, 10);
        ExternalServiceEntity entity = new ExternalServiceEntity();
        Page<ExternalServiceEntity> entityPage = new PageImpl<>(List.of(entity));
        ExternalServiceAdminResponse response = new ExternalServiceAdminResponse(1L, "login", OffsetDateTime.now());

        when(externalServiceRepository.findAll(pageable)).thenReturn(entityPage);
        when(externalServiceMapper.entityToResponse(entity)).thenReturn(response);

        Page<ExternalServiceAdminResponse> result = externalServiceAdminService.list(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(externalServiceRepository).findAll(pageable);
    }

    @Test
    void create_ShouldEncryptBothSecretsAndSave() {
        String passHash = "hashed_pass";
        String secretHash = "hashed_secret";
        ExternalServiceEntity entity = new ExternalServiceEntity();
        ExternalServiceAdminResponse expectedResponse = new ExternalServiceAdminResponse(1L, "login", OffsetDateTime.now());

        when(externalServiceRepository.existsByLogin(request.login())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn(passHash);
        when(passwordEncoder.encode(request.webhookSecret())).thenReturn(secretHash);

        when(externalServiceMapper.requestToEntity(request, passHash, secretHash))
            .thenReturn(entity);
        when(externalServiceRepository.save(entity)).thenReturn(entity);
        when(externalServiceMapper.entityToResponse(entity)).thenReturn(expectedResponse);

        ExternalServiceAdminResponse result = externalServiceAdminService.create(request);

        assertNotNull(result);
        verify(passwordEncoder, times(2)).encode(anyString());
        verify(externalServiceRepository).save(entity);
    }

    @Test
    void create_ShouldThrowException_WhenLoginExists() {
        when(externalServiceRepository.existsByLogin(request.login())).thenReturn(true);

        assertThrows(ExternalServiceLoginException.class, () ->
            externalServiceAdminService.create(request)
        );

        verifyNoInteractions(passwordEncoder);
        verify(externalServiceRepository, never()).save(any());
    }
}