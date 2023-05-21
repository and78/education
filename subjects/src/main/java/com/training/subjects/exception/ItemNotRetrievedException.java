package com.training.subjects.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotRetrievedException extends RuntimeException {

    private static final String MESSAGE = "Item cannot be retrieved";

    public ItemNotRetrievedException() {
        super(MESSAGE);
    }
}
