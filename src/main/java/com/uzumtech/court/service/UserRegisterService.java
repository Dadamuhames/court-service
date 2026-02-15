package com.uzumtech.court.service;

import com.uzumtech.court.entity.UserEntity;

public interface UserRegisterService {

    UserEntity findUserByPinflOrRegister(String pinfl);

    UserEntity registerUserByPinfl(String pinfl);

}
