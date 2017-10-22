package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.repository.VerificationTokenRepository;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.ec.ECDHKeyAgreement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    private static final String name = "name";
    private static final String surname = "surname";
    private static final String username = "username";
    private static final String password = "password";
    private static final String email = "e@mail.com";

    private static UserTO userTO;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Before
    public void initRepoWithCorrectData() throws RegistrationException {
        verificationTokenRepository.deleteAll();
        userRepository.deleteAll();
        userTO = new UserTO();
        userTO.setName(name);
        userTO.setSurname(surname);
        userTO.setUsername(username);
        userTO.setPassword(password);
        userTO.setMatchingPassword(password);
        userTO.setEmail(email);
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithSameEmailShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("e@mail.com");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithSameUsernameShouldThrowException() throws Exception {
        userTO.setUsername("username");
        userTO.setEmail("e2@mail.com");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithNotMatchingPasswordShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("e2@mail.com");
        userTO.setMatchingPassword("password2");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithNullEmailShouldThrowException() throws Exception{
        userTO.setUsername("username2");
        userTO.setEmail(null);
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithEmailWithoutAtSignShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("mail");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithPasswordShorterThen8SignsShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("e2@mail.com");
        userTO.setPassword("pass");
        userTO.setMatchingPassword("pass");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithUsernameShorterThen4SignsShouldThrowException() throws Exception {
        userTO.setUsername("u");
        userTO.setEmail("e2@mail.com");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithNullNameShouldThrowException() throws Exception{
        userTO.setUsername("username2");
        userTO.setEmail("e2@mail.com");
        userTO.setName(null);
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithEmptyNameShouldThrowException() throws Exception{
        userTO.setUsername("username2");
        userTO.setEmail("e2@mail.com");
        userTO.setName("");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithNullSurnameShouldThrowException() throws Exception{
        userTO.setUsername("username2");
        userTO.setEmail("e2@mail.com");
        userTO.setSurname(null);
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithEmptySurnameShouldThrowException() throws Exception{
        userTO.setUsername("username2");
        userTO.setEmail("e2@mail.com");
        userTO.setSurname("");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithInvalidEmailShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("mail@");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithInvalidEmail2ShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("@mail");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithInvalidEmail3ShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("@mail.com");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithInvalidEmail4ShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail("mail@.com");
        userService.registerUser(userTO);
    }

    @Test(expected = RegistrationException.class)
    public void registerUserWithInvalidEmail5ShouldThrowException() throws Exception {
        userTO.setUsername("username2");
        userTO.setEmail(".@mail.com");
        userService.registerUser(userTO);
    }
}