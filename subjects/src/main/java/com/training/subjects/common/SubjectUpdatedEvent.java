package com.training.subjects.common;

import com.training.subjects.dto.SubjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectUpdatedEvent {

    SubjectDto subject;

}
