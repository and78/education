package com.training.course.repository;

import com.training.course.domain.Course;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CourseRepository extends ReactiveMongoRepository<Course, String> {

    Flux<Course> findAllBySubjectsId(Long subjectId);

}
