package com.training.common.exception;

public class ObjectConversionException extends RuntimeException {

    private static final String MESSAGE = "Error while converting objet";

    public ObjectConversionException() {
        super(MESSAGE);
    }
}
