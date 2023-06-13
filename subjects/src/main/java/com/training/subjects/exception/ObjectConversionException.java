package com.training.subjects.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectConversionException extends RuntimeException {

    private static final String MESSAGE = "Error while converting objet";

    public ObjectConversionException() {
        super(MESSAGE);
    }
}
