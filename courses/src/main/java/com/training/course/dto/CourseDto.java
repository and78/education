package com.training.course.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CourseDto {

    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> subjectIds = new ArrayList<>();

    private Long professorId;

}
