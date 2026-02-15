package com.uzumtech.court.service.impl.admin;


import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.request.admin.ExternalServiceCreateRequest;
import com.uzumtech.court.dto.response.admin.ExternalServiceAdminResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.exception.ExternalServiceLoginException;
import com.uzumtech.court.mapper.ExternalServiceMapper;
import com.uzumtech.court.repository.ExternalServiceRepository;
import com.uzumtech.court.service.ExternalServiceAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class ExternalServiceAdminServiceImpl implements ExternalServiceAdminService {
    private final ExternalServiceRepository externalServiceRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExternalServiceMapper externalServiceMapper;

    @Transactional(readOnly = true)
    public Page<ExternalServiceAdminResponse> list(final Pageable pageable) {
        Page<ExternalServiceEntity> services = externalServiceRepository.findAll(pageable);

        return services.map(externalServiceMapper::entityToResponse);
    }

    @Transactional
    public ExternalServiceAdminResponse create(@Valid @RequestBody final ExternalServiceCreateRequest request) {
        validateLogin(request);

        String encryptedPassword = passwordEncoder.encode(request.password());
        String webhookSecretEncrypted = passwordEncoder.encode(request.webhookSecret());

        ExternalServiceEntity externalServiceEntity = externalServiceMapper.requestToEntity(request, encryptedPassword, webhookSecretEncrypted);

        externalServiceEntity = externalServiceRepository.save(externalServiceEntity);

        return externalServiceMapper.entityToResponse(externalServiceEntity);
    }

    private void validateLogin(ExternalServiceCreateRequest request) {
        boolean existsByLogin = externalServiceRepository.existsByLogin(request.login());

        if (existsByLogin) {
            throw new ExternalServiceLoginException(ErrorCode.EXTERNAL_SERVICE_LOGIN);
        }
    }
}
