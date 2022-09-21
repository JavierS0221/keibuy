package com.project.auction.exception;

/**
 * Exception thrown by system in case some one try to register with already exisiting email
 * id in the system.
 */
public class EmailAlreadyUsedException extends Exception {

    public EmailAlreadyUsedException() {
        super();
    }


    public EmailAlreadyUsedException(String message) {
        super(message);
    }


    public EmailAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}