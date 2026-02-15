package com.uzumtech.court.service;

import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.exception.OffenseNotFoundException;

import java.util.List;

public interface OffenseService {

    OffenseEntity findById(Long id) throws OffenseNotFoundException;

    OffenseResponse register(ExternalServiceEntity externalService, OffenseRegistrationRequest request);
}
