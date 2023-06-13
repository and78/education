package com.training.subjects.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.subjects.exception.ObjectConversionException;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Slf4j
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class JsonMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String fromObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            throw new ObjectConversionException();
        }
    }

    public <T> T fromJsonToObject(String json, Class<T> tClass) {
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            throw new ObjectConversionException();
        }
    }
}
