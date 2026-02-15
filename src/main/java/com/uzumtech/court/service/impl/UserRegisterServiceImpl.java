package com.uzumtech.court.service.impl;

import com.uzumtech.court.component.adapter.GcpAdapter;
import com.uzumtech.court.dto.response.GcpResponse;
import com.uzumtech.court.entity.UserEntity;
import com.uzumtech.court.mapper.UserMapper;
import com.uzumtech.court.repository.UserRepository;
import com.uzumtech.court.service.UserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final GcpAdapter gcpAdapter;

    @Override
    @Transactional
    public UserEntity findUserByPinflOrRegister(String pinfl) {
        UserEntity user = userRepository.findByPinfl(pinfl).orElse(null);

        if (user == null) user = registerUserByPinfl(pinfl);

        return user;
    }

    @Override
    @Transactional
    public UserEntity registerUserByPinfl(String pinfl) {
        GcpResponse gcpResponse = gcpAdapter.fetchUserInfoByPinfl(pinfl);

        UserEntity newUser = userMapper.gcpResponseToUserEntity(gcpResponse);

        return userRepository.save(newUser);
    }
}
