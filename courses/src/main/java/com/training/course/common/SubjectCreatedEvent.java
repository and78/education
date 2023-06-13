package com.training.course.common;

import com.training.course.dto.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectCreatedEvent {

    private String courseId;

    private List<SubjectDto> subjects = new ArrayList<>();

}
