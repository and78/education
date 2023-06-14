package com.training.course.mapper;

import com.training.common.dto.CommonSubjectDto;
import com.training.common.event.CourseCommandEvent;
import com.training.course.domain.Course;
import com.training.course.domain.Subject;
import com.training.course.dto.CourseDto;
import com.training.course.dto.SubjectDto;
import com.training.course.web.resources.CourseRequest;
import com.training.course.web.resources.CourseResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class CourseMapperTest {

    @InjectMocks
    private CourseMapper courseMapper = new CourseMapperImpl();

    @Spy
    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @Test
    void shouldMapToCourseDto() {
        CourseRequest courseRequest = new CourseRequest(
                "Course 1",
                LocalDate.now(),
                LocalDate.now().plus(1, ChronoUnit.DAYS),
                List.of(1L, 2L, 3L),
                "Professor 1"
        );
        CourseDto courseDto = courseMapper.toCourseDto(courseRequest);
        assertThat(courseDto.getId(), is(nullValue()));
        assertThat(courseDto.getName(), is(courseRequest.name()));
        assertThat(courseDto.getStartDate(), is(courseRequest.startDate()));
        assertThat(courseDto.getEndDate(), is(courseRequest.endDate()));
        assertThat(courseDto.getSubjects().stream().map(CommonSubjectDto::getId).toList(), is(courseRequest.subjectIds()));
    }

    @Test
    void shouldMapToEntity() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");

        CourseDto courseDto = new CourseDto();
        courseDto.setId("Course 1");
        courseDto.setName("New course");
        courseDto.setStartDate(LocalDate.now());
        courseDto.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        courseDto.setSubjects(List.of(subjectDto));

        Course course = courseMapper.toEntity(courseDto);
        assertThat(course.getId(), is(courseDto.getId()));
        assertThat(course.getName(), is(courseDto.getName()));
        assertThat(course.getStartDate(), is(courseDto.getStartDate()));
        assertThat(course.getEndDate(), is(courseDto.getEndDate()));
        assertThat(courseDto.getSubjects().stream()
                .allMatch(dto -> dto.getId().equals(subjectDto.getId())
                        && dto.getName().equals(subjectDto.getName())), is(true));
    }

    @Test
    void shouldMapToResponse() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");

        CourseDto courseDto = new CourseDto();
        courseDto.setId("Course 1");
        courseDto.setName("New course");
        courseDto.setStartDate(LocalDate.now());
        courseDto.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        courseDto.setSubjects(List.of(subjectDto));

        CourseResponse courseResponse = courseMapper.toResponse(courseDto);

        assertThat(courseResponse.id(), is(courseDto.getId()));
        assertThat(courseResponse.name(), is(courseDto.getName()));
        assertThat(courseResponse.startDate(), is(courseDto.getStartDate()));
        assertThat(courseResponse.endDate(), is(courseDto.getEndDate()));
        assertThat(courseDto.getSubjects().stream()
                .allMatch(dto -> dto.getId().equals(subjectDto.getId())
                        && dto.getName().equals(subjectDto.getName())), is(true));
    }

    @Test
    void shouldMapToCourseDtoFromCourse() {
        Subject subject = new Subject();
        subject.setId(100L);
        subject.setName("Subject 1");

        Course course = new Course();
        course.setId("Course 1");
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        course.setSubjects(List.of(subject));

        CourseDto courseDto = courseMapper.toCourseDto(course);
        assertThat(courseDto.getId(), is(course.getId()));
        assertThat(courseDto.getName(), is(course.getName()));
        assertThat(courseDto.getStartDate(), is(course.getStartDate()));
        assertThat(courseDto.getEndDate(), is(course.getEndDate()));
        assertThat(courseDto.getSubjects().stream()
                .allMatch(dto -> dto.getId().equals(subject.getId())
                        && dto.getName().equals(subject.getName())), is(true));
    }

    @Test
    void shouldMapToEvent() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");

        CourseDto courseDto = new CourseDto();
        courseDto.setId("Course 1");
        courseDto.setName("New course");
        courseDto.setStartDate(LocalDate.now());
        courseDto.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        courseDto.setSubjects(List.of(subjectDto));

        CourseCommandEvent event = courseMapper.toEvent(courseDto);
        assertThat(event.getCourseId(), is(courseDto.getId()));
        assertThat(event.getSubjectIds().stream().findFirst().orElse(0L),
                is(courseDto.getSubjects().stream().findFirst().orElse(new SubjectDto()).getId()));
    }
}