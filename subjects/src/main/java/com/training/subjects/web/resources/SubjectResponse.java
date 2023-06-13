package com.training.subjects.web.resources;

public record SubjectResponse(Long id,
                              String name,
                              String description,
                              String professorName) {
}
