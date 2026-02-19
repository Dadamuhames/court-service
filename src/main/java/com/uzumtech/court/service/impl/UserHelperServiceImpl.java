package com.uzumtech.court.service.impl;

import com.uzumtech.court.entity.UserEntity;
import com.uzumtech.court.repository.UserRepository;
import com.uzumtech.court.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserHelperServiceImpl implements UserHelperService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }
}
