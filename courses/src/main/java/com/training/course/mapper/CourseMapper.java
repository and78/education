package com.training.course.mapper;

import com.training.common.event.CourseCommandEvent;
import com.training.course.domain.Course;
import com.training.course.dto.CourseDto;
import com.training.course.web.resources.CourseRequest;
import com.training.course.web.resources.CourseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = SubjectMapper.class)
public interface CourseMapper {

    CourseDto toCourseDto(Course course);

    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseDto course);

    CourseResponse toResponse(CourseDto courseDto);

    @Mapping(target = "subjects", source = "subjectIds")
    @Mapping(target = "id", ignore = true)
    CourseDto toCourseDto(CourseRequest courseRequest);

    @Mapping(target = "courseId", source = "id")
    @Mapping(target = "subjectIds", source = "subjects")
    CourseCommandEvent toEvent(CourseDto course);

}
