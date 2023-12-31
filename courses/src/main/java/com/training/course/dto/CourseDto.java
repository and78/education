package com.training.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {

    private String id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<SubjectDto> subjects = new ArrayList<>();

}
