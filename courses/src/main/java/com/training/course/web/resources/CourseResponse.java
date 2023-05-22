package com.training.course.web.resources;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record CourseResponse(
        String id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        List<Long> subjectIds,
        Long professorId) {

    public CourseResponse {
        if (Objects.isNull(subjectIds)) subjectIds = Collections.emptyList();
    }
}
