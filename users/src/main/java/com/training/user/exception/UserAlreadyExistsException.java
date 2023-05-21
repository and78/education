package com.training.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.training.user.config.Constants.MESSAGE;
import static com.training.user.config.Constants.STATUS;
import static com.training.user.config.Constants.TIMESTAMP;

@Getter
public class UserAlreadyExistsException extends RuntimeException implements ErrorMapAttributes {

    private static final String REASON = "User already exists";

    public UserAlreadyExistsException() {
        super(REASON);
    }

    @Override
    public Map<String, Object> getErrorMap() {
        final Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, this.getMessage());
        errorMap.put(TIMESTAMP, LocalDateTime.now());
        errorMap.put(STATUS, HttpStatus.BAD_REQUEST);
        return errorMap;
    }
}
