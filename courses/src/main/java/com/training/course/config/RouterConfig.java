package com.training.course.config;

import com.training.course.exception.HandlerException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static com.training.course.config.Constants.EMPTY;
import static com.training.course.config.Constants.ID;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final CoursesRouteHandler coursesRouteHandler;

    @Bean
    public RouterFunction<ServerResponse> highLevelRouter() {
        return RouterFunctions.route()
                .path("api", this::courseRouterFunction)
                .build();
    }

    public RouterFunction<ServerResponse> courseRouterFunction() {
        return RouterFunctions.route()
                .GET(EMPTY, request -> coursesRouteHandler.getAllCourses())
                .GET("/{" + ID + "}", coursesRouteHandler::getCourseById)
                .POST(EMPTY, coursesRouteHandler::createCourse)
                .PUT("/{" + ID + "}", coursesRouteHandler::updateCourse)
                .DELETE("/{" + ID + "}", coursesRouteHandler::deleteCourse)
                .onError(RuntimeException.class, exceptionHandler())
                .build();
    }


    private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
        return (err, req) -> ServerResponse.badRequest().bodyValue(new HandlerException());
    }

}
