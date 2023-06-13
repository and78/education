package com.training.course.mapper;

import com.training.course.domain.Subject;
import com.training.course.dto.SubjectDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class SubjectMapper {

    public abstract Subject toSubject(SubjectDto dto);

    public abstract SubjectDto toSubjectDto(Subject dto);

    public abstract List<Subject> toSubjectCollection(List<SubjectDto> dto);

    public SubjectDto toSubjectDto(Long subjectId) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subjectId);
        return subjectDto;
    }

    public abstract List<SubjectDto> toSubjectDtos(List<Long> subjectIds);

    public List<Long> toSubjectIdCollection(List<SubjectDto> subjectDtos) {
        return subjectDtos.stream()
                .map(SubjectDto::getId)
                .toList();
    }

    @Named("toSubjectId")
    public Long toSubjectId(Subject subject) {
        return subject.getId();
    }

    @Named("toSubject")
    public Subject toSubject(Long subjectId) {
        Subject subject = new Subject();
        subject.setId(subjectId);
        return subject;
    }

}
