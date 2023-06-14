package com.training.subjects.config;

import com.training.subjects.config.broker.ConsumerConfig;
import com.training.subjects.config.broker.ProviderConfig;
import com.training.subjects.config.broker.TopicConfig;
import com.training.subjects.listener.ConsumerListener;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class SubjectsConfig {

    @MockBean
    private ConsumerConfig consumerConfig;

    @MockBean
    private ProviderConfig providerConfig;

    @MockBean
    private TopicConfig topicConfig;

    @MockBean
    private ConsumerListener consumerListener;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplateList;
}
