package com.project.auction.exception;

/**
In case customer account does not exists in the system for a given email id.
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