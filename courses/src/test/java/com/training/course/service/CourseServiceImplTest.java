package com.training.course.service;

import com.training.common.mapper.JsonMapper;
import com.training.common.mapper.JsonMapperImpl;
import com.training.course.domain.Course;
import com.training.course.dto.CourseDto;
import com.training.course.mapper.CourseMapper;
import com.training.course.mapper.CourseMapperImpl;
import com.training.course.mapper.SubjectMapper;
import com.training.course.mapper.SubjectMapperImpl;
import com.training.course.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EventProducerService eventProducerService;

    private JsonMapper jsonMapper = new JsonMapperImpl();

    @InjectMocks
    private CourseMapper courseMapper = new CourseMapperImpl();

    @Spy
    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @BeforeEach
    void setUp() {
        courseService =
                new CourseServiceImpl(courseRepository, courseMapper, eventProducerService, jsonMapper);
    }

    @Test
    void shouldGetAllCourses() {
        Course course = new Course();
        course.setId("Course 1");
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        when(courseRepository.findAll()).thenReturn(Flux.fromIterable(List.of(course)));

        StepVerifier.create(courseService.getAllCourses())
                .expectNextMatches(courseDto -> courseDto.getId().equals(course.getId())
                        && courseDto.getName().equals(course.getName())
                        && courseDto.getStartDate().equals(course.getStartDate())
                        && courseDto.getEndDate().equals(course.getEndDate()))
                .verifyComplete();

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void shouldGetCourseById() {
        String id = "QWA12-BA344";

        Course course = new Course();
        course.setId(id);
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        when(courseRepository.findById(id)).thenReturn(Mono.just(course));

        StepVerifier.create(courseService.getCourseById(id))
                .expectNextMatches(courseDto -> courseDto.getId().equals(course.getId())
                        && courseDto.getName().equals(course.getName())
                        && courseDto.getStartDate().equals(course.getStartDate())
                        && courseDto.getEndDate().equals(course.getEndDate()))
                .verifyComplete();

        verify(courseRepository, times(1)).findById(id);

    }

    @Test
    void shouldCreateCourse() {
        CourseDto courseDto = new CourseDto();
        courseDto.setId("Course 1");
        courseDto.setName("New course");
        courseDto.setStartDate(LocalDate.now());
        courseDto.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        Course course = new Course();
        course.setId("Course 1");
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        when(courseRepository.save(any(Course.class))).thenReturn(Mono.just(course));

        StepVerifier.create(courseService.createCourse(Mono.just(courseDto)))
                .expectNextMatches(dto -> dto.getId().equals(courseDto.getId())
                        && dto.getName().equals(courseDto.getName())
                        && dto.getStartDate().equals(courseDto.getStartDate())
                        && dto.getEndDate().equals(courseDto.getEndDate()))
                .verifyComplete();

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldUpdateCourse() {
        String id = "Course 1";

        CourseDto courseDto = new CourseDto();
        courseDto.setId(id);
        courseDto.setName("New course");
        courseDto.setStartDate(LocalDate.now());
        courseDto.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        Course course = new Course();
        course.setId(id);
        course.setName("New course");
        course.setStartDate(LocalDate.now());
        course.setEndDate(LocalDate.now().plus(1, ChronoUnit.DAYS));

        when(courseRepository.findById(id)).thenReturn(Mono.just(course));
        when(courseRepository.save(any(Course.class))).thenReturn(Mono.just(course));

        StepVerifier.create(courseService.updateCourse(id, Mono.just(courseDto)))
                .expectNextMatches(dto -> dto.getId().equals(courseDto.getId())
                        && dto.getName().equals(courseDto.getName())
                        && dto.getStartDate().equals(courseDto.getStartDate())
                        && dto.getEndDate().equals(courseDto.getEndDate()))
                .verifyComplete();

        verify(courseRepository, times(1)).findById(id);
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void shouldDeleteCourse() {
        String id = "QWA12-BA344";

        when(courseRepository.deleteById(id)).thenReturn(Mono.empty());

        StepVerifier.create(courseService.deleteCourse(id))
                .verifyComplete();

        verify(courseRepository, times(1)).deleteById(id);
    }
}