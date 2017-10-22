package com.ksm.robolo.roboloapp.services;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendMail(SimpleMailMessage mailMessage);
}
