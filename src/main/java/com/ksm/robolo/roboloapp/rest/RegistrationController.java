package com.ksm.robolo.roboloapp.rest;

import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.PasswordsNotMatchingException;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.services.exceptions.UserEmailConstraintViolationException;
import com.ksm.robolo.roboloapp.services.exceptions.UsernameConstraintViolationException;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final static String REGISTRATION_SUCCESS_MSG = "REGISTRATION SUCCESSFUL";

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<String> registerUser(@ModelAttribute UserTO userTO) {

        try {
            userService.registerUser(userTO);
        } catch (RegistrationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(REGISTRATION_SUCCESS_MSG, HttpStatus.CREATED);
    }
}
