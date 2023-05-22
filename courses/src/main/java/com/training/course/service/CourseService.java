package com.training.course.service;

import com.training.course.dto.CourseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CourseService {

    Flux<CourseDto> getAllCourses();

    Mono<CourseDto> getCourseById(String id);

    Mono<CourseDto> createCourse(Mono<CourseDto> courseDto);

    Mono<CourseDto> updateCourse(String id, Mono<CourseDto> courseDto);

    Mono<Void> deleteCourse(String id);

}
