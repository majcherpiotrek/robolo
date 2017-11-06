package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.domain.VerificationToken;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.repository.VerificationTokenRepository;
import com.ksm.robolo.roboloapp.services.EmailService;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.*;
import com.ksm.robolo.roboloapp.tos.RetrievePasswordTO;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.Date;
import java.util.UUID;
import static java.util.Collections.emptyList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static final String DUPLICATE_USER_EMAIL_ERROR = "User with this email address already exists.";
    private static final String DUPLICATE_USERNAME_ERROR = "User with this username already exists.";
    private static final String PASSWORDS_NOT_MATCHING_ERROR = "Entered passwords are not matching.";;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final VerificationTokenRepository verificationTokenRepository;

    private final EmailService emailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
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
    @Transactional
    public void createVerificationToken(String token, UserTO user) {
        VerificationToken userToken = new VerificationToken();
        userToken.setToken(token);
        UserEntity userEntity = userRepository.findByEmail(user.getEmail());
        userToken.setUser(userEntity);
        verificationTokenRepository.save(userToken);
    }

    @Override
    public void confirmUser(String token) throws RegistrationException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        Date now = new Date();
        if (verificationToken == null || now.after(verificationToken.getExpiryDate()) ) {

            if (verificationToken != null) {
                UUID uuid = verificationToken.getUser().getId();
                verificationTokenRepository.delete(verificationToken.getId());
                userRepository.delete(uuid);
            }

            throw new RegistrationException("We are sorry, your token is invalid or expired.");
        }

        UserEntity userEntity = findByVerificationToken(token);

        if (userEntity == null) {
            throw new RegistrationException("Could not verify - user does not exist!");
        }

        userRepository.setUserEnabled(userEntity.getId());
        logger.info("User \'" + userEntity.getUsername() + "\' has been verified!");
    }

    @Override
    public void retrievePasswordByUsername(String username) throws RetrievePasswordException {

        UserEntity user = userRepository.findByUsername(username);

        retrievePassword(user);
    }

    private void retrievePassword(UserEntity user) throws RetrievePasswordException {
        if (user == null) {
            throw new RetrievePasswordException("User with username \'" + user.getUsername() + "\' does not exist!");
        }

        VerificationToken token = verificationTokenRepository.findByUser(user);
        if (token != null) {
            verificationTokenRepository.delete(token);
        }

        token = new VerificationToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        verificationTokenRepository.save(token);

        sendRetrieveTokenEmail(token.getToken(), user);
    }

    @Override
    public void retrievePasswordByEmail(String email) throws RetrievePasswordException {
        UserEntity user = userRepository.findByEmail(email);

        retrievePassword(user);
    }

    @Override
    public void changeRetrievedPassword(RetrievePasswordTO retrievePasswordTO) throws RetrievePasswordException {
        validateRetrievedPassword(retrievePasswordTO);

        VerificationToken verificationToken = verificationTokenRepository.findByToken(retrievePasswordTO.getToken());

        if (verificationToken == null) {
            throw new RetrievePasswordException("We are sorry, your token is invalid or expired.");
        }

        if (verificationToken.getUser() == null) {
            throw new RetrievePasswordException("We are sorry, there is no user connected to this token!");
        }

        try {
            UserEntity userEntity = userRepository.findOne(verificationToken.getUser().getId());

            if (userEntity == null) {
                throw new RetrievePasswordException("We are sorry, there is no user connected to this token!");
            }

            userRepository.setNewPassword(passwordEncoder.encode(retrievePasswordTO.getPassword()), userEntity.getId());
        } catch (Exception e) {
            throw new RetrievePasswordException(e.getMessage());
        }
    }

    private void validateRetrievedPassword(RetrievePasswordTO retrievePasswordTO) {
        Assert.notNull(retrievePasswordTO, "Please provide new password!");
        Assert.isTrue(retrievePasswordTO.getPassword().equals(retrievePasswordTO.getMatchingPassword()), "The passwords don't match!");
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

    @Transactional
    private void saveUser(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
        logger.info("Saved new user: " + userEntity.getUsername());
    }

    private void sendRetrieveTokenEmail(String token, UserEntity user) {
        String recipientAddress = user.getEmail();
        String subject = "Robolify - retrieve password";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("robolify@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Copy this token to change your password: " + token);
        emailService.sendMail(email);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUsername(username);
		
		if (userEntity == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(userEntity.getUsername(), userEntity.getPassword(), emptyList());
	}
}
