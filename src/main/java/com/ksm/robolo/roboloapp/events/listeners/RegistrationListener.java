package com.ksm.robolo.roboloapp.events.listeners;

import com.ksm.robolo.roboloapp.events.OnRegistrationCompleteEvent;
import com.ksm.robolo.roboloapp.services.EmailService;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.tos.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;

    private final EmailService emailService;

    @Autowired
    public RegistrationListener(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        sendRegistrationConfirmationEmail(onRegistrationCompleteEvent);
    }

    private void sendRegistrationConfirmationEmail(OnRegistrationCompleteEvent event) {
        UserTO user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(token, user);

        String recipientAddress = user.getEmail();
        String subject = "Robolify - registration confirmation";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("robolify@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Click this link to confirm your account: " + event.getAppUrl() + token);
        emailService.sendMail(email);
    }
}
