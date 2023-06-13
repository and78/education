package com.training.subjects.listener;

import com.training.subjects.common.CourseCommandEvent;
import com.training.subjects.mapper.JsonMapper;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.repository.SubjectRepository;
import com.training.subjects.service.EventProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ConsumerListener {

    private final SubjectRepository subjectRepository;
    private final JsonMapper jsonMapper;
    private final EventProducerService eventProducerService;
    private final SubjectMapper subjectMapper;

    @KafkaListener(topics = "${kafka.topic.subjects-service.name}",
            groupId = "${kafka.topic.subjects-service.groupId}")
    public void listener(String message) {
        Optional.ofNullable(jsonMapper.fromJsonToObject(message, CourseCommandEvent.class))
                .map(event ->
                        subjectMapper.toEvent(
                                event.getCourseId(),
                                subjectRepository.findAllById(event.getSubjectIds()))
                )
                .map(jsonMapper::fromObjectToJson)
                .ifPresent(eventProducerService::sendMessageToCoursesService);
    }

}
