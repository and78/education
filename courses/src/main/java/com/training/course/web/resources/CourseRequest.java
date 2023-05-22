package com.training.course.web.resources;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record CourseRequest(

        @NotEmpty(message = "name must not be empty")
        String name,

        @FutureOrPresent(message = "startDate must be a date in the present or in the future")
        LocalDate startDate,

        @FutureOrPresent(message = "endDate must be a date in the present or in the future")
        LocalDate endDate,

        List<Long> subjectIds,

        Long professorId) {

    public CourseRequest {
        if (Objects.isNull(subjectIds)) subjectIds = Collections.emptyList();
    }
}
