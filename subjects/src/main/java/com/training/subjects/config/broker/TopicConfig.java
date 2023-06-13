package com.training.subjects.config.broker;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class TopicConfig {

    @Value("${kafka.topic.subjects-service.name}")
    private String topicName;

    @Bean
    public NewTopic generateTopic() {
        final Map<String, String> configurations =
                Map.of(org.apache.kafka.common.config.TopicConfig.CLEANUP_POLICY_CONFIG, org.apache.kafka.common.config.TopicConfig.CLEANUP_POLICY_DELETE,
                        org.apache.kafka.common.config.TopicConfig.RETENTION_MS_CONFIG, "86400000");

        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .configs(configurations)
                .build();
    }

}
