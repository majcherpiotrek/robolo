package com.ksm.robolo.roboloapp.services.impl;

import com.ksm.robolo.roboloapp.services.EmailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    @Async
    public void sendMail(SimpleMailMessage mailMessage) {
        mailSender.send(mailMessage);
    }
}
