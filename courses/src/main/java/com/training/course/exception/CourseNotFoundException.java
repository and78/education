package com.training.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.training.course.config.Constants.MESSAGE;
import static com.training.course.config.Constants.STATUS;
import static com.training.course.config.Constants.TIMESTAMP;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CourseNotFoundException extends RuntimeException implements ErrorMapAttributes{

    private static final String REASON = "Course not found";

    public CourseNotFoundException() {
        super(REASON);
    }

    @Override
    public Map<String, Object> getErrorMap() {
        final Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, this.getMessage());
        errorMap.put(TIMESTAMP, LocalDateTime.now());
        errorMap.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        return errorMap;
    }
}
