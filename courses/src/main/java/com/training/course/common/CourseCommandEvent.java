package com.training.course.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCommandEvent {

    private String courseId;

    private List<Long> subjectIds = new ArrayList<>();

}
