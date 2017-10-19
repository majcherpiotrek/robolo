package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.domain.UserEntity;
import com.ksm.robolo.roboloapp.services.LoginService;
import com.ksm.robolo.roboloapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserService userService;

    @Autowired
    public LoginServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Override
    public boolean validateUser(String username, String password) {
        boolean validated = false;
        UserEntity userEntity = userService.findByUsername(username);

        if (userEntity != null) {
            String hashedPassword = bCryptPasswordEncoder.encode(password);
            if (hashedPassword.equals(userEntity.getPassword())) {
                validated = true;
            }
        }
        return validated;
    }
}
