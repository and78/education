package com.training.course.web.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {

    private Long id;

    private String name;

    private String description;

    private String professorName;

}
