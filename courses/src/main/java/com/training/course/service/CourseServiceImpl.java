package com.training.course.service;

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

    private final CourseRepository repository;
    private final CourseMapper mapper;

    @Override
    public Flux<CourseDto> getAllCourses() {
        return this.repository.findAll()
                .map(mapper::toCourseDto);
    }

    @Override
    public Mono<CourseDto> getCourseById(String id) {
        return this.repository.findById(id)
                .map(mapper::toCourseDto)
                .switchIfEmpty(Mono.error(CourseNotFoundException::new));
    }

    @Override
    public Mono<CourseDto> createCourse(Mono<CourseDto> courseDto) {
        return courseDto.map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toCourseDto);
    }

    @Override
    public Mono<CourseDto> updateCourse(String id, Mono<CourseDto> courseDto) {
        return repository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(CourseNotFoundException::new)))
                .flatMap(course -> courseDto.map(mapper::toEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(repository::save)
                .map(mapper::toCourseDto);
    }

    @Override
    public Mono<Void> deleteCourse(String id) {
        return repository.deleteById(id);
    }
}
