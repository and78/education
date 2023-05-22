package com.training.course.config;

import com.training.course.exception.RequestValidatorException;
import com.training.course.mapper.CourseMapper;
import com.training.course.service.CourseService;
import com.training.course.web.resources.CourseRequest;
import com.training.course.web.resources.CourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.training.course.config.Constants.ID;

@Service
@RequiredArgsConstructor
public class CoursesRouteHandler {

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final Validator courseValidator;

    public Mono<ServerResponse> getAllCourses() {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(courseService.getAllCourses(), CourseResponse.class);
    }

    public Mono<ServerResponse> getCourseById(final ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable(ID);
        return courseService.getCourseById(id)
                .flatMap(courseDto -> ServerResponse.ok().bodyValue(courseDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createCourse(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CourseRequest.class)
                .map(this::validateResource)
                .map(courseRequestMono -> courseRequestMono.map(courseMapper::toCourseDto))
                .map(courseService::createCourse)
                .map(courseDtoMono -> courseDtoMono.map(courseMapper::toResponse))
                .flatMap(courseResponse ->
                        ServerResponse.ok().body(courseResponse, CourseResponse.class));
    }

    public Mono<ServerResponse> updateCourse(final ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable(ID);
        return serverRequest.bodyToMono(CourseRequest.class)
                .map(this::validateResource)
                .map(courseRequestMono -> courseRequestMono.map(courseMapper::toCourseDto))
                .map(courseDtoMono -> courseService.updateCourse(id, courseDtoMono))
                .map(courseDtoMono -> courseDtoMono.map(courseMapper::toResponse))
                .flatMap(courseResponse ->
                        ServerResponse.ok().body(courseResponse, CourseResponse.class));
    }

    public Mono<ServerResponse> deleteCourse(final ServerRequest serverRequest) {
        final String id = serverRequest.pathVariable(ID);
        return ServerResponse.ok()
                .body(courseService.deleteCourse(id), Void.class);
    }

    private Mono<CourseRequest> validateResource(final CourseRequest courseRequest) {
        final Errors errors = new BeanPropertyBindingResult(courseRequest, "courseRequest");
        courseValidator.validate(courseRequest, errors);
        if (errors.hasErrors()) {
            return Mono.defer(() ->
                    Mono.error(new RequestValidatorException(errors)));
        }
        return Mono.defer(() -> Mono.just(courseRequest));
    }
}
