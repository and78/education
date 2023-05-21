package com.training.subjects.config;

import com.training.subjects.dto.ErrorResponseDto;
import com.training.subjects.exception.SubjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(SubjectNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(final SubjectNotFoundException exception) {
        log.error(String.format("Error message: %s", exception.getMessage()), exception);
        final var response = ErrorResponseDto.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleUnknownException(final Exception exception) {
        log.error(String.format("Error message: %s", exception.getMessage()), exception);
        final var response = ErrorResponseDto.builder()
                .message("Unexpected error")
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
