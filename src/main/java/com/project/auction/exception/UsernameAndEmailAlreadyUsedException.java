package com.project.auction.exception;

/**
In case customer account does not exists in the system for a given email id.
 */
public class UsernameAndEmailAlreadyUsedException extends Exception {

    public UsernameAndEmailAlreadyUsedException() {
        super();
    }


    public UsernameAndEmailAlreadyUsedException(String message) {
        super(message);
    }


    public UsernameAndEmailAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }
}