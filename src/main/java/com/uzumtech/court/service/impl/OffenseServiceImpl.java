package com.uzumtech.court.service.impl;


import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.constant.enums.OffenseStatus;
import com.uzumtech.court.dto.request.OffenseRegistrationRequest;
import com.uzumtech.court.dto.response.OffenseResponse;
import com.uzumtech.court.entity.ExternalServiceEntity;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.entity.OffenseEntity;
import com.uzumtech.court.entity.UserEntity;
import com.uzumtech.court.exception.judge.JudgeNotFoundException;
import com.uzumtech.court.exception.offense.OffenseNotFoundException;
import com.uzumtech.court.exception.offense.OffenseRegisteredException;
import com.uzumtech.court.mapper.OffenseMapper;
import com.uzumtech.court.repository.JudgeRepository;
import com.uzumtech.court.repository.OffenseRepository;
import com.uzumtech.court.service.OffenseService;
import com.uzumtech.court.service.UserRegisterService;
import com.uzumtech.court.utils.CourtCaseNumberUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OffenseServiceImpl implements OffenseService {
    private final OffenseRepository offenseRepository;
    private final UserRegisterService userRegisterService;
    private final CourtCaseNumberUtils courtCaseNumberUtils;
    private final JudgeRepository judgeRepository;
    private final OffenseMapper offenseMapper;

    @Override
    @Transactional(readOnly = true)
    public OffenseEntity findById(Long id) throws OffenseNotFoundException {
        return offenseRepository.findById(id).orElseThrow(() -> new OffenseNotFoundException(ErrorCode.OFFENSE_NOT_FOUND_CODE));
    }

    @Override
    @Transactional
    public OffenseResponse register(final ExternalServiceEntity externalService, final OffenseRegistrationRequest request) {
        validateExternalOffenseId(request);

        UserEntity user = userRegisterService.findUserByPinflOrRegister(request.offenderPinfl());
        JudgeEntity judge = judgeRepository.findRandomJudge().orElseThrow(() -> new JudgeNotFoundException(ErrorCode.JUDGE_NOT_FOUND_CODE));

        String courtCaseNumber = courtCaseNumberUtils.generateProtocolNumber();

        OffenseEntity offenseEntity = offenseMapper.requestToEntity(request, user, judge, courtCaseNumber);

        offenseEntity.setExternalService(externalService);

        offenseEntity = offenseRepository.save(offenseEntity);

        return offenseMapper.entityToResponse(offenseEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIdAndProcessing(Long id) {
        return offenseRepository.existsByIdAndProcessing(id);
    }

    private void validateExternalOffenseId(final OffenseRegistrationRequest request) {
        boolean offenseRegistered = offenseRepository.existsByExternalId(request.legalOffenseId());

        if (offenseRegistered) {
            throw new OffenseRegisteredException(ErrorCode.OFFENSE_REGISTERED_CODE);
        }
    }

    @Transactional
    public void changeStatus(Long id, OffenseStatus status) {
        offenseRepository.updateStatus(id, status);
    }
}
