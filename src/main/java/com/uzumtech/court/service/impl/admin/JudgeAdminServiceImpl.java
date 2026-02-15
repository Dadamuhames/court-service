package com.uzumtech.court.service.impl.admin;

import com.uzumtech.court.constant.enums.ErrorCode;
import com.uzumtech.court.dto.request.admin.JudgeRequest;
import com.uzumtech.court.dto.response.admin.JudgeResponse;
import com.uzumtech.court.entity.JudgeEntity;
import com.uzumtech.court.exception.JudgeEmailExistsException;
import com.uzumtech.court.mapper.JudgeMapper;
import com.uzumtech.court.repository.JudgeRepository;
import com.uzumtech.court.service.JudgeAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JudgeAdminServiceImpl implements JudgeAdminService {
    private final JudgeRepository judgeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JudgeMapper judgeMapper;

    @Transactional(readOnly = true)
    public Page<JudgeResponse> list(final Pageable pageable) {

        Page<JudgeEntity> judges = judgeRepository.findAll(pageable);

        return judges.map(judgeMapper::entityToResponse);
    }

    @Transactional
    public JudgeResponse create(final JudgeRequest request) {
        validateJudgeEmail(request);

        String encodedPassword = passwordEncoder.encode(request.password());

        JudgeEntity judge = judgeMapper.requestToEntity(request, encodedPassword);

        judge = judgeRepository.save(judge);

        return judgeMapper.entityToResponse(judge);
    }


    private void validateJudgeEmail(final JudgeRequest request) {
        boolean existsByEmail = judgeRepository.existsByEmail(request.email());

        if (existsByEmail) {
            throw new JudgeEmailExistsException(ErrorCode.JUDGE_EMAIL_EXISTS_CODE);
        }
    }
}
