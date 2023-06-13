package com.training.course.domain;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(value = "courses")
public class Course {

    @Nonnull
    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Subject> subjects = new ArrayList<>();

}
