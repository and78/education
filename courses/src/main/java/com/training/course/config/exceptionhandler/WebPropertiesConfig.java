package com.training.course.config.exceptionhandler;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WebPropertiesConfig {

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

}
