package com.ksm.robolo.roboloapp.services.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionSystemException;

@Component
public class ExceptionUnwrapper {
	
	public String getExceptionMessage(Exception e) {
		
		String errorMessage = null;
        if (e instanceof TransactionSystemException) {
            Throwable cause = e.getCause();
            while ( (cause != null) && !(cause instanceof ConstraintViolationException) ) {
                cause = cause.getCause();
            }

            if (cause != null) {
                StringBuilder message = new StringBuilder();
                Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)cause).getConstraintViolations();
                for (ConstraintViolation<?> violation : violations) {
                    message.append(violation.getMessage());
                }
                errorMessage = message.toString();
            }
        }

        if (errorMessage == null) {
            errorMessage = e.getMessage();
        }
        
        return errorMessage;
	}

}
