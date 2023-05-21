package com.training.subjects.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ErrorResponseDto {

    private final String message;
    private final List<String> errors = new ArrayList<>();
    private final LocalDateTime timestamp;

}
