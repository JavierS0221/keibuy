package com.project.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String mensaje) {
        super(mensaje);
    }

    public FileNotFoundException(String mensaje, Throwable excepcion) {
        super(mensaje, excepcion);
    }

}
