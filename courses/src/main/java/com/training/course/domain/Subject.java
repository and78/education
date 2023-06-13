package com.training.course.domain;

import jakarta.annotation.Nonnull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Subject {

    @Nonnull
    private Long id;

    private String name;

    private String description;

    private String professorName;

}
