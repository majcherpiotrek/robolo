package com.ksm.robolo.roboloapp.services.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ksm.robolo.roboloapp.services.EmailService;
import com.ksm.robolo.roboloapp.services.exceptions.EmailServiceException;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EmailServiceImpl implements EmailService{

    private static final Logger logger = Logger.getLogger(EmailServiceImpl.class);
    
    private static final String FAILED_TO_SEND_REGISTRATION_CONFIRMATION_EMAIL = "Failed to send registration confirmation email";
    
    @Override
    @Async
    public void sendMail(String recipientAddress, String subject, String content) throws EmailServiceException {
    	Email from = new Email("app82315571@heroku.com");
		Email to = new Email(recipientAddress);
		Content mailContent = new Content("text/plain", content);
		
		Mail mail = new Mail(from, subject, to, mailContent);
		
		SendGrid sg = new SendGrid(System.getenv("PIWIND_API_KEY"));
		Request request = new Request();
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
		    request.setBody(mail.build());
		    Response response = sg.api(request);
		    logger.info(response.getStatusCode());
		    if (response.getStatusCode() != 200 && response.getStatusCode() != 202) {
		    	throw new EmailServiceException(FAILED_TO_SEND_REGISTRATION_CONFIRMATION_EMAIL);
		    }
		    logger.info(response.getBody());
		    logger.info(response.getHeaders());
		} catch (IOException e) {
		      throw new EmailServiceException(FAILED_TO_SEND_REGISTRATION_CONFIRMATION_EMAIL);
	    }
    }
}
