package com.training.common.event;

import com.training.common.dto.CommonSubjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectUpdatedEvent {

    CommonSubjectDto subject;

}
