package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.domain.UserEntity;

public interface UserService {

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    void saveUser(UserEntity userEntity);
}
