package com.training.course.service;

import com.training.common.event.CourseCommandEvent;
import com.training.common.mapper.JsonMapper;
import com.training.course.dto.CourseDto;
import com.training.course.exception.CourseNotFoundException;
import com.training.course.mapper.CourseMapper;
import com.training.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final EventProducerService eventProducerService;
    private final JsonMapper jsonMapper;

    @Override
    public Flux<CourseDto> getAllCourses() {
        return this.courseRepository.findAll()
                .map(courseMapper::toCourseDto);
    }

    @Override
    public Mono<CourseDto> getCourseById(String id) {
        return this.courseRepository.findById(id)
                .map(courseMapper::toCourseDto)
                .switchIfEmpty(Mono.error(CourseNotFoundException::new));
    }

    @Override
    public Mono<CourseDto> createCourse(Mono<CourseDto> courseDto) {
        return courseDto.map(courseMapper::toEntity)
                .flatMap(courseRepository::save)
                .map(courseMapper::toCourseDto)
                .doOnSuccess(dto -> {
                    final CourseCommandEvent courseCommandEvent = courseMapper.toEvent(dto);
                    eventProducerService.sendMessageToSubjectsService(jsonMapper.fromObjectToJson(courseCommandEvent));
                });
    }

    @Override
    public Mono<CourseDto> updateCourse(String id, Mono<CourseDto> courseDto) {
        return courseRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(CourseNotFoundException::new)))
                .flatMap(course -> courseDto.map(courseMapper::toEntity))
                .flatMap(courseRepository::save)
                .map(courseMapper::toCourseDto)
                .doOnSuccess(dto -> {
                    final CourseCommandEvent courseCommandEvent = courseMapper.toEvent(dto);
                    eventProducerService.sendMessageToSubjectsService(jsonMapper.fromObjectToJson(courseCommandEvent));
                });
    }

    @Override
    public Mono<Void> deleteCourse(String id) {
        return courseRepository.deleteById(id);
    }
}
