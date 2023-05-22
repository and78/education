package com.training.course.exception;

public class HandlerException extends RuntimeException {

    private static final String MESSAGE = "Error while processing request";

    public HandlerException() {
        super(MESSAGE);
    }
}
