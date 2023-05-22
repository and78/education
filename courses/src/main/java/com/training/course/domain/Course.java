package com.training.course.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Document
public class Course {

    @Id
    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> subjectIds = new ArrayList<>();

    private Long professorId;

}
