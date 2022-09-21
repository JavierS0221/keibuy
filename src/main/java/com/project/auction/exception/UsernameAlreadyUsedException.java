package com.project.auction.exception;

/**
In case customer account does not exists in the system for a given email id.
 */
public class UsernameAlreadyUsedException extends Exception {

    public UsernameAlreadyUsedException() {
        super();
    }


    public UsernameAlreadyUsedException(String message) {
        super(message);
    }


    public UsernameAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}