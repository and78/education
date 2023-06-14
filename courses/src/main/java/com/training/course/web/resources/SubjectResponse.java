package com.training.course.web.resources;

public record SubjectResponse(
        Long id,
        String name,
        String description,
        String professorName) {
}
