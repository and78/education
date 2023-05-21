package com.training.user.config.exceptionhandler;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

import static com.training.user.config.Constants.STATUS;

@Component
@Order(-2)
public class ApplicationExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ApplicationExceptionHandler(ErrorAttributes errorAttributes,
                                       WebProperties.Resources resources,
                                       ApplicationContext applicationContext,
                                       ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        super.setMessageReaders(configurer.getReaders());
        super.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderException);
    }

    private Mono<ServerResponse> renderException(ServerRequest request) {
        final Map<String, Object> error = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        final HttpStatus status = Optional.of(error)
                .map(err -> err.getOrDefault(STATUS, HttpStatus.BAD_REQUEST))
                .filter(HttpStatus.class::isInstance)
                .map(HttpStatus.class::cast)
                .orElse(HttpStatus.BAD_REQUEST);
        error.remove(STATUS);
        return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(error));
    }

}
