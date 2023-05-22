package com.training.course.exception;

import lombok.Getter;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.server.ServerWebInputException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.training.course.config.Constants.ERRORS;
import static com.training.course.config.Constants.MESSAGE;
import static com.training.course.config.Constants.STATUS;
import static com.training.course.config.Constants.TIMESTAMP;

@Getter
public class RequestValidatorException extends ServerWebInputException implements ErrorMapAttributes {

    private static final String REASON = "Invalid request object";

    private final List<String> errors;

    public RequestValidatorException(final Errors err) {
        super(REASON);
        errors = err.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

    @Override
    public Map<String, Object> getErrorMap() {
        final Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, this.getReason());
        errorMap.put(ERRORS, this.getErrors());
        errorMap.put(TIMESTAMP, LocalDateTime.now());
        errorMap.put(STATUS, HttpStatus.BAD_REQUEST);
        return errorMap;
    }
}
