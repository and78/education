package com.training.common.event;

import com.training.common.dto.CommonSubjectDto;
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

    private List<CommonSubjectDto> subjects = new ArrayList<>();

}
