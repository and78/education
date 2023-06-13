package com.training.subjects.service;

public interface EventProducerService {

    void sendMessageToCoursesService(String json);

    void sendMessageToCoursesServiceSubjectUpdated(final String json);

}
