package com.training.course.common;

import com.training.course.dto.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectUpdatedEvent {

    SubjectDto subject;

}
