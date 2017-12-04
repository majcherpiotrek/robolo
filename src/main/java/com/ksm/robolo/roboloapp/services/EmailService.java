package com.ksm.robolo.roboloapp.services;

import com.ksm.robolo.roboloapp.services.exceptions.EmailServiceException;

public interface EmailService {

    void sendMail(String recipientAddress, String subject, String content) throws EmailServiceException;
}
