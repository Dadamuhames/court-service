package com.uzumtech.court.service;

import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.exception.offense.OffenseNotFoundException;

public interface OffenseService {

    OffenseEntity findById(Long id) throws OffenseNotFoundException;

    OffenseResponse register(ExternalServiceEntity externalService, OffenseRegistrationRequest request);

    boolean existsByIdAndProcessing(Long id);

    void changeStatus(Long id, OffenseStatus status);
}
