package com.training.course.service;

public interface EventProducerService {

    void sendMessageToSubjectsService(String jsonStr);

}
