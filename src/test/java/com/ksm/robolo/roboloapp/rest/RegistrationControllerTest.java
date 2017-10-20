package com.ksm.robolo.roboloapp.rest;

import com.google.gson.*;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;
import com.sun.javafx.collections.MappingChange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest {

    public static class UserSerializer implements JsonSerializer<UserTO> {
        public JsonElement serialize(final UserTO userTO, final Type type, final JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("name", new JsonPrimitive(userTO.getName()));
            result.add("surname", new JsonPrimitive(userTO.getSurname()));
            result.add("username", new JsonPrimitive(userTO.getUsername()));
            result.add("password", new JsonPrimitive(userTO.getPassword()));
            result.add("matchingPassword", new JsonPrimitive(userTO.getMatchingPassword()));
            result.add("email", new JsonPrimitive(userTO.getEmail()));
            return result;
        }
    }

    private final static String REGISTRATION_SUCCESS_MSG = "REGISTRATION SUCCESSFUL";
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
    private UserService userService;

    @Before
    public void initUserTO() throws RegistrationException {
        userTO = new UserTO();
        userTO.setName(name);
        userTO.setSurname(surname);
        userTO.setUsername(username);
        userTO.setPassword(password);
        userTO.setMatchingPassword(password);
        userTO.setEmail(email);
        userService.registerUser(userTO);
    }

//    @Test
//    public void registerValidUserShouldReturnSuccessMessageAndHttpStatusCREATED() throws InterruptedException {
//        //TODO WTF the request gets send twice, so the test goes well first time (the user is saved), and then fails. See the logs
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
//
//        Gson gson = new GsonBuilder().registerTypeAdapter(UserTO.class, new UserSerializer()).create();
//        map.add("userTO", gson.toJson(userTO));
//
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        assertTrue(HttpStatus.CREATED.equals(restTemplate.postForEntity(LOCALHOST + port + "/register", request, String.class).getStatusCode()));
//    }
}