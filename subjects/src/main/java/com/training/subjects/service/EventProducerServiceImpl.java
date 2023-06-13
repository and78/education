package com.training.subjects.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class EventProducerServiceImpl implements EventProducerService {

    private final KafkaTemplate<String, String> kafkaTemplateList;

    @Value("${kafka.topic.courses-service.name}")
    private String topicNameCourse;

    @Value("${kafka.topic.courses-subject-updated.name}")
    private String topicNameCourseSubjectUpdated;

    @Override
    public void sendMessageToCoursesService(final String json) {
        kafkaTemplateList.send(topicNameCourse, json);
    }

    @Override
    public void sendMessageToCoursesServiceSubjectUpdated(final String json) {
        kafkaTemplateList.send(topicNameCourseSubjectUpdated, json);
    }

}
