package com.ksm.robolo.roboloapp.rest;

import com.ksm.robolo.roboloapp.events.OnRegistrationCompleteEvent;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.RegistrationException;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class RegistrationController {
    private static final Logger logger = Logger.getLogger(RegistrationController.class);

    private final static String REGISTRATION_SUCCESS_MSG = "Registration successful!";

    private final UserService userService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private static final String CONFIRMATION_URL= "http://robolify-server.herokuapp.com/confirm/";

    @Autowired
    public RegistrationController(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    @CrossOrigin
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> registerUser(HttpServletRequest request, @RequestBody UserTO userTO) {
        logger.info("Received request: " + userTO.toString());
        try {

            userService.registerUser(userTO);
            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(userTO, request.getLocale(), CONFIRMATION_URL));
        } catch (RegistrationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(REGISTRATION_SUCCESS_MSG, HttpStatus.CREATED);
    }
    
    @CrossOrigin
    @GetMapping(value = "/confirm/{verificationToken}")
    public ResponseEntity<String> confirmUser(@PathVariable String verificationToken) {
        try {
            userService.confirmUser(verificationToken);
        } catch (RegistrationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("Your account has been activated!", HttpStatus.OK);
    }
}
