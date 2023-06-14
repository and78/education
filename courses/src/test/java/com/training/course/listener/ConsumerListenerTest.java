package com.training.course.listener;

import com.training.common.dto.CommonSubjectDto;
import com.training.common.event.SubjectCreatedEvent;
import com.training.common.event.SubjectUpdatedEvent;
import com.training.common.mapper.JsonMapper;
import com.training.common.mapper.JsonMapperImpl;
import com.training.course.domain.Course;
import com.training.course.mapper.SubjectMapper;
import com.training.course.mapper.SubjectMapperImpl;
import com.training.course.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumerListenerTest {

    @InjectMocks
    private ConsumerListener consumerListener;

    @Mock
    private CourseRepository courseRepository;

    @Spy
    private JsonMapper jsonMapper = new JsonMapperImpl();

    @Spy
    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @Test
    void shouldPerformListenerCoursesCreatedEvent() {
        String id = "Course 1";

        CommonSubjectDto commonSubjectDto = new CommonSubjectDto();
        commonSubjectDto.setId(100L);
        commonSubjectDto.setName("Subject 1");
        commonSubjectDto.setDescription("Description 1");
        commonSubjectDto.setProfessorName("Professor 1");

        SubjectCreatedEvent subjectCreatedEvent = new SubjectCreatedEvent();
        subjectCreatedEvent.setCourseId(id);
        subjectCreatedEvent.setSubjects(List.of(commonSubjectDto));

        Course course = new Course();
        course.setId(id);
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        when(courseRepository.findById(id)).thenReturn(Mono.just(course));
        when(courseRepository.save(any(Course.class))).thenReturn(Mono.just(course));

        consumerListener.listenerCoursesCreatedEvent(jsonMapper.fromObjectToJson(subjectCreatedEvent));

        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldPerformListenerSubjectUpdatedEvent() {
        long id = 100L;

        CommonSubjectDto commonSubjectDto = new CommonSubjectDto();
        commonSubjectDto.setId(id);
        commonSubjectDto.setName("Subject 1");
        commonSubjectDto.setDescription("Description 1");
        commonSubjectDto.setProfessorName("Professor 1");

        SubjectUpdatedEvent subjectUpdatedEvent = new SubjectUpdatedEvent();
        subjectUpdatedEvent.setSubject(commonSubjectDto);

        Course course = new Course();
        course.setId("Course 1");
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        when(courseRepository.findAllBySubjectsId(id)).thenReturn(Flux.fromIterable(List.of(course)));
        when(courseRepository.saveAll(anyList())).thenReturn(Flux.fromIterable(List.of(course)));

        consumerListener.listenerSubjectUpdatedEvent(jsonMapper.fromObjectToJson(subjectUpdatedEvent));

        verify(courseRepository, times(1)).findAllBySubjectsId(id);
        verify(courseRepository, times(1)).saveAll(anyList());
    }
}