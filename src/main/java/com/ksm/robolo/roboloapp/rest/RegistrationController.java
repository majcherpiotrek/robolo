package com.ksm.robolo.roboloapp.rest;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public String registerUser(@ModelAttribute UserTO userTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userTO.getName());
        userEntity.setSurname(userTO.getSurname());
        userEntity.setUsername(userTO.getUsername());
        userEntity.setPassword(userTO.getPassword());
        userEntity.setEmail(userTO.getEmail());
        userService.saveUser(userEntity);
        return "registered";

    }
}
