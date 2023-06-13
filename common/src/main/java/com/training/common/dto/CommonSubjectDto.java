package com.training.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonSubjectDto {

    private Long id;

    private String name;

    private String description;

    private String professorName;

}
