package com.project.auction.exception;

public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable exception) {
        super(message, exception);
    }
}
