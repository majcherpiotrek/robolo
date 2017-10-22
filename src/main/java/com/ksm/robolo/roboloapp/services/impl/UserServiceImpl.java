package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.VerificationToken;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.repository.VerificationTokenRepository;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.PasswordsNotMatchingException;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.services.exceptions.UserEmailConstraintViolationException;
import com.ksm.robolo.roboloapp.services.exceptions.UsernameConstraintViolationException;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static final String DUPLICATE_USER_EMAIL_ERROR = "User with this email address already exists.";
    private static final String DUPLICATE_USERNAME_ERROR = "User with this username already exists.";
    private static final String PASSWORDS_NOT_MATCHING_ERROR = "Entered passwords are not matching.";;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void registerUser(UserTO userTO) throws RegistrationException {

        try {
            validateUserRegistrationData(userTO);
            UserEntity userEntity = new UserEntity();
            userEntity.setName(userTO.getName());
            userEntity.setSurname(userTO.getSurname());
            userEntity.setUsername(userTO.getUsername());
            userEntity.setPassword(userTO.getPassword());
            userEntity.setEmail(userTO.getEmail());

            saveUser(userEntity);
        } catch (Exception e) {

            String errorMessage = null;
            if (e instanceof TransactionSystemException) {
                Throwable cause = e.getCause();
                while ( (cause != null) && !(cause instanceof ConstraintViolationException) ) {
                    cause = cause.getCause();
                }

                if (cause != null) {
                    StringBuilder message = new StringBuilder();
                    Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)cause).getConstraintViolations();
                    for (ConstraintViolation<?> violation : violations) {
                        message.append(violation.getMessage());
                    }
                    errorMessage = message.toString();
                }
            }

            if (errorMessage == null) {
                errorMessage = e.getMessage();
            }

            logger.error("Exception occurred while trying to register new user: " + errorMessage + "\n");
            e.printStackTrace();
            throw new RegistrationException(errorMessage);
        }
    }

    @Override
    public UserEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUsername = authentication.getName();
            userEntity = userRepository.findByUsername(currentUsername);
        }
        return userEntity;
    }

    @Override
    public void createVerificationToken(String token, UserTO user) {
        VerificationToken userToken = new VerificationToken();
        userToken.setToken(token);
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        userToken.setUser(userEntity);
        verificationTokenRepository.save(userToken);
    }

    @Override
    public void confirmUser(String verificationToken) throws RegistrationException {
        UserEntity userEntity = findByVerificationToken(verificationToken);

        if (userEntity == null) {
            throw new RegistrationException("Could not verify - user does not exist!");
        }

        userRepository.setUserEnabled(userEntity.getId());
        logger.info("User \'" + userEntity.getUsername() + "\' has been verified!");
    }

    private UserEntity findByVerificationToken(String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken).getUser();
    }

    private void validateUserRegistrationData(UserTO userTO) throws UserEmailConstraintViolationException, UsernameConstraintViolationException, PasswordsNotMatchingException {
        Assert.notNull(userTO.getPassword(), "Please provide a password");
        Assert.isTrue(userTO.getPassword().length() >= 8, "The password must be at least 8 characters" );
        UserEntity userEntity = findByEmail(userTO.getEmail());

        if (userEntity != null) {
            throw new UserEmailConstraintViolationException(DUPLICATE_USER_EMAIL_ERROR);
        }

        userEntity = findByUsername(userTO.getUsername());

        if (userEntity != null) {
            throw new UsernameConstraintViolationException(DUPLICATE_USERNAME_ERROR);
        }

        if (!userTO.getPassword().equals(userTO.getMatchingPassword())) {
            throw new PasswordsNotMatchingException(PASSWORDS_NOT_MATCHING_ERROR);
        }
    }

    private void saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        logger.info("Saved new user: " + userEntity.getUsername());
    }


}
