package com.training.course.config;

import com.training.common.mapper.JsonMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapperConfig {

    @Bean
    public JsonMapper jsonMapper() {
        return Mappers.getMapper(JsonMapper.class);
    }

}
