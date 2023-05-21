package com.training.user.config.exceptionhandler;

import com.training.user.exception.ErrorMapAttributes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.training.user.config.Constants.MESSAGE;
import static com.training.user.config.Constants.STATUS;
import static com.training.user.config.Constants.TIMESTAMP;

@Component
@Primary
@Slf4j
public class GlobalErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        final Throwable error = getError(request);

        if (error instanceof ErrorMapAttributes exception)
            return exception.getErrorMap();
        else
            return getExceptionAttributes(error);
    }

    private Map<String, Object> getExceptionAttributes(final Throwable error) {
        log.error(error.getMessage());
        final Map<String, Object> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, "Unexpected error");
        errorMap.put(TIMESTAMP, LocalDateTime.now());
        errorMap.put(STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        return errorMap;
    }

}
