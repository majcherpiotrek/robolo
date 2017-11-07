package com.ksm.robolo.roboloapp.services;

import java.util.UUID;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.VerificationToken;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.services.exceptions.RetrievePasswordException;
import com.ksm.robolo.roboloapp.tos.RetrievePasswordTO;
import com.ksm.robolo.roboloapp.tos.UserTO;

public interface UserService {

    void registerUser(UserTO userTO) throws RegistrationException;

    void createVerificationToken(String token, UserTO userEntity);

    void confirmUser(String verificationToken) throws RegistrationException;

    void retrievePasswordByUsername(String username) throws RetrievePasswordException;

    void retrievePasswordByEmail(String email) throws RetrievePasswordException;

    void changeRetrievedPassword(RetrievePasswordTO retrievePasswordTO) throws RetrievePasswordException;

	UUID getUserId(String username);
}
