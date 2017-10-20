package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;

public interface UserService {

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    void registerUser(UserTO userTO) throws RegistrationException;

    UserEntity getLoggedInUser();
}
