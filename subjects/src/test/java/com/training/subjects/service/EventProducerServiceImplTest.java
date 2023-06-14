package com.training.subjects.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventProducerServiceImplTest {

    private EventProducerService eventProducerService;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplateList;

    @BeforeEach
    void setUp() {
        eventProducerService = new EventProducerServiceImpl(kafkaTemplateList);
    }


    @Test
    void shouldSendMessageToCoursesService() {
        when(kafkaTemplateList.send(any(), anyString())).thenReturn(new CompletableFuture<>());

        eventProducerService.sendMessageToCoursesService("{}");

        verify(kafkaTemplateList, times(1)).send(any(), anyString());

    }

    @Test
    void shouldSendMessageToCoursesServiceSubjectUpdated() {
        when(kafkaTemplateList.send(any(), anyString())).thenReturn(new CompletableFuture<>());

        eventProducerService.sendMessageToCoursesServiceSubjectUpdated("{}");

        verify(kafkaTemplateList, times(1)).send(any(), anyString());

    }
}