package com.ksm.robolo.roboloapp.events.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ksm.robolo.roboloapp.events.OnRegistrationCompleteEvent;
import com.ksm.robolo.roboloapp.services.EmailService;
import com.ksm.robolo.roboloapp.services.UserService;
import com.ksm.robolo.roboloapp.services.exceptions.EmailServiceException;
import com.ksm.robolo.roboloapp.tos.UserTO;

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
        String content = "Click this link to confirm your account: " + event.getAppUrl() + token;

        try {
			emailService.sendMail(recipientAddress, subject, content);
		} catch (EmailServiceException e) {
			e.printStackTrace();
		}
    }
}
