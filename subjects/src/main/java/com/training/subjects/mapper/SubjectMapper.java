package com.training.subjects.mapper;

import com.training.subjects.common.SubjectCreatedEvent;
import com.training.subjects.domain.Subject;
import com.training.subjects.dto.SubjectDto;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface SubjectMapper {

    SubjectResponse toResponse(SubjectDto subjectDto);

    @Mapping(target = "id", ignore = true)
    SubjectDto toDto(SubjectRequest request);

    SubjectDto toDto(Subject request);

    Subject toEntity(SubjectDto request);

    SubjectCreatedEvent toEvent(String courseId, List<Subject> subjects);

}
