package com.training.subjects.web.resources;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SubjectRequest(

        @NotEmpty(message = "name must not be empty")
        String name,

        String description,

        @NotNull(message = "professorId mut not be null")
        Long professorId) {
}
