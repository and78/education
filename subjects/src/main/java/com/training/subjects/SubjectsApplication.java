package com.training.subjects;

import com.training.subjects.config.properties.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class SubjectsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubjectsApplication.class, args);
    }

}
