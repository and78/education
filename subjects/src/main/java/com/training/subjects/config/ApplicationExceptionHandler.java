package com.training.subjects.config;

import com.training.subjects.dto.ErrorResponseDto;
import com.training.subjects.exception.SubjectNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final String ERROR_MESSAGE_TEXT = "Error message: %s";

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(final SubjectNotFoundException exception) {
        log.error(String.format(ERROR_MESSAGE_TEXT, exception.getMessage()), exception);
        final var response = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(final ConstraintViolationException exception) {
        log.error(String.format(ERROR_MESSAGE_TEXT, exception.getMessage()), exception);
        final var response = ErrorResponseDto.builder()
                .message(exception.getConstraintViolations()
                        .stream()
                        .findFirst()
                        .map(ConstraintViolation::getMessage)
                        .orElseThrow(() -> new RuntimeException(exception)))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnknownException(final Exception exception) {
        log.error(String.format(ERROR_MESSAGE_TEXT, exception.getMessage()), exception);
        final var response = ErrorResponseDto.builder()
                .message("Unexpected error")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
