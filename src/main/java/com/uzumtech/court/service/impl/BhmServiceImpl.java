package com.uzumtech.court.service.impl;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.entity.BhmAmountEntity;
import com.uzumtech.court.exception.BhmNotSpecifiedException;
import com.uzumtech.court.repository.BhmAmountRepository;
import com.uzumtech.court.service.BhmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BhmServiceImpl implements BhmService {
    private final BhmAmountRepository bhmAmountRepository;

    @Transactional(readOnly = true)
    public BhmAmountEntity getCurrentAmount() {
        return bhmAmountRepository.findCurrentAmount().orElseThrow(() -> new BhmNotSpecifiedException(ErrorCode.BHM_NOT_SPECIFIED_CODE));
    }
}
