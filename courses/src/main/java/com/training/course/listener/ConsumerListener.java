package com.training.course.listener;

import com.training.common.event.SubjectCreatedEvent;
import com.training.common.event.SubjectUpdatedEvent;
import com.training.common.mapper.JsonMapper;
import com.training.course.domain.Course;
import com.training.course.domain.Subject;
import com.training.course.dto.SubjectDto;
import com.training.course.mapper.SubjectMapper;
import com.training.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ConsumerListener {

    private final CourseRepository courseRepository;
    private final JsonMapper jsonMapper;
    private final SubjectMapper subjectMapper;

    @KafkaListener(topics = "${kafka.topic.courses-service.name}",
            groupId = "${kafka.topic.courses-service.groupId}")
    public void listenerCoursesCreatedEvent(final String message) {
        final SubjectCreatedEvent subjectCreatedEvent = jsonMapper.fromJsonToObject(message, SubjectCreatedEvent.class);
        courseRepository.findById(subjectCreatedEvent.getCourseId())
                .filter(course -> !subjectCreatedEvent.getSubjects().isEmpty())
                .doOnNext(course -> course.setSubjects(subjectMapper.toSubjectCollection(subjectCreatedEvent.getSubjects())))
                .flatMap(courseRepository::save)
                .subscribe();
    }

    @KafkaListener(topics = "${kafka.topic.courses-subject-updated.name}",
            groupId = "${kafka.topic.courses-subject-updated.groupId}")
    public void listenerSubjectUpdatedEvent(final String message) {
        final SubjectUpdatedEvent subjectUpdatedEvent = jsonMapper.fromJsonToObject(message, SubjectUpdatedEvent.class);
        Optional.ofNullable(subjectUpdatedEvent)
                .map(SubjectUpdatedEvent::getSubject)
                .filter(subjectDto -> Objects.nonNull(subjectDto.getId()))
                .map(subjectMapper::toSubjectDto)
                .ifPresent(subjectDto ->
                        courseRepository.findAllBySubjectsId(subjectDto.getId())
                                .map(course -> getCourseWithNewSubjects(subjectDto, course))
                                .collectList()
                                .doOnSuccess(courseList -> courseRepository.saveAll(courseList).subscribe())
                                .subscribe()
                );
    }

    private Course getCourseWithNewSubjects(final SubjectDto subjectDto, final Course course) {
        final List<Subject> subjects = course.getSubjects()
                .stream()
                .filter(subject -> !subject.getId().equals(subjectDto.getId()))
                .collect(Collectors.toList());
        subjects.add(subjectMapper.toSubject(subjectDto));
        course.setSubjects(Collections.unmodifiableList(subjects));
        return course;
    }

}
