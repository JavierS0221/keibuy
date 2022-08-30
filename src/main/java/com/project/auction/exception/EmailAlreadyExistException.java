package com.project.auction.exception;

/**
 * Exception thrown by system in case some one try to register with already exisiting email
 * id in the system.
 */
public class EmailAlreadyExistException extends Exception {

    public EmailAlreadyExistException() {
        super();
    }


    public EmailAlreadyExistException(String message) {
        super(message);
    }


    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}