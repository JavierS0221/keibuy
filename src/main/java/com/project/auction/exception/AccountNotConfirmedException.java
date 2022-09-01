package com.project.auction.exception;


import org.springframework.security.core.AuthenticationException;

public class AccountNotConfirmedException extends AuthenticationException {

    public AccountNotConfirmedException(String message) {
        super(message);
    }


    public AccountNotConfirmedException(String message, Throwable cause) {
        super(message, cause);
    }
}