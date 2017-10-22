package com.ksm.robolo.roboloapp.rest;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.ksm.robolo.roboloapp.RoboloAppApplication;
import com.ksm.robolo.roboloapp.repository.UserRepository;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoboloAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest {

    private static final Logger logger = Logger.getLogger(RegistrationControllerTest.class);

    private static final String LOCALHOST = "http://localhost:";

    private UserTO userTO;
    private static final String name = "name";
    private static final String surname = "surname";
    private static final String username = "piotrek";
    private static final String password = "password";
    private static final String email = "e@mail.com";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void init() throws RegistrationException {
        userTO = new UserTO();
        userTO.setName(name);
        userTO.setSurname(surname);
        userTO.setUsername(username);
        userTO.setPassword(password);
        userTO.setMatchingPassword(password);
        userTO.setEmail(email);

        MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonHttpMessageConverter.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        restTemplate.getRestTemplate().getMessageConverters().add(jsonHttpMessageConverter);

        userRepository.deleteAll();
    }

    @Test
    public void registerValidUserShouldReturnSuccessMessageAndHttpStatusCREATED() {
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserTO> httpEntity = new HttpEntity<>(userTO, headers);

        String url = LOCALHOST + port + "/register";
        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        logger.info("Response: " + responseEntity.getBody());
    }

    @Test
    public void registerUserWithSameEmailShouldReturnHttpStatusCONFLICT() {
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserTO> httpEntity = new HttpEntity<>(userTO, headers);

        String url = LOCALHOST + port + "/register";
        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);

        userTO.setUsername(username + 2);
        httpEntity = new HttpEntity<>(userTO, headers);

        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        logger.info("Response: " + responseEntity.getBody());
    }

    @Test
    public void registerUserWithSameUsernameShouldReturnHttpStatusCONFLICT() {
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserTO> httpEntity = new HttpEntity<>(userTO, headers);

        String url = LOCALHOST + port + "/register";
        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);

        userTO.setEmail("bla@bla.com");
        httpEntity = new HttpEntity<>(userTO, headers);

        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        logger.info("Response: " + responseEntity.getBody());
    }

    @Test
    public void registerEmptyUserShouldReturnHttpStatusConflict() {
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<UserTO> httpEntity = new HttpEntity<>(new UserTO(), headers);

        String url = LOCALHOST + port + "/register";
        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);


        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        logger.info("Response: " + responseEntity.getBody());
    }

    @Test
    public void registerUserWithInvalidEmailAddressShouldReturnHttpStatusCONFLICT() {
        HttpHeaders  headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        userTO.setEmail("mail");
        HttpEntity<UserTO> httpEntity = new HttpEntity<>(userTO, headers);

        String url = LOCALHOST + port + "/register";
        logger.info("Sending request to: " + url);
        logger.info("HttpEntity being sent: " + httpEntity.getBody());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        logger.info("Response: " + responseEntity.getBody());
    }
}