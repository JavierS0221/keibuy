package com.project.auction.exception;

/**
 * Exception thrown by system in case some one try to register with already exisiting username
 * id in the system.
 */
public class UsernameAlreadyExistException extends Exception {

    public UsernameAlreadyExistException() {
        super();
    }


    public UsernameAlreadyExistException(String message) {
        super(message);
    }


    public UsernameAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}