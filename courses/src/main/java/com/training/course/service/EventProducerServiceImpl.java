package com.training.course.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
class EventProducerServiceImpl implements EventProducerService {

    private final KafkaTemplate<String, String> kafkaTemplateList;

    @Value("${kafka.topic.subjects-service.name}")
    private String topicGetSubjects;

    @Override
    public void sendMessageToSubjectsService(final String json) {
        kafkaTemplateList.send(topicGetSubjects, json);
    }

}
