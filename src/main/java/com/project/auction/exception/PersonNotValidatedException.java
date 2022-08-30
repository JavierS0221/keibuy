package com.project.auction.exception;

/**
In case customer account does not exists in the system for a given email id.
 */
public class PersonNotValidatedException extends Exception {

    public PersonNotValidatedException() {
        super();
    }


    public PersonNotValidatedException(String message) {
        super(message);
    }


    public PersonNotValidatedException(String message, Throwable cause) {
        super(message, cause);
    }
}