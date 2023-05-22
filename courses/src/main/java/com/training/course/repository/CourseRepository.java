package com.training.course.repository;

import com.training.course.domain.Course;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ReactiveMongoRepository<Course, String> {

}
