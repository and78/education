package com.training.user.config.router;

import com.training.user.dto.UserDto;
import com.training.user.exception.RequestValidatorException;
import com.training.user.mapper.UserMapper;
import com.training.user.service.UserService;
import com.training.user.web.resources.UserRequest;
import com.training.user.web.resources.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.training.user.config.Constants.ID;

@Service
@RequiredArgsConstructor
public class UserRouterHandler {

    private final UserService userService;
    private final UserMapper userMapper;
    private final Validator userValidator;

    public Mono<ServerResponse> findAllUsers(final ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.findAll(), UserDto.class);
    }

    public Mono<ServerResponse> findUserById(final ServerRequest serverRequest) {
        final Long id = Long.valueOf(serverRequest.pathVariable(ID));
        return userService.findUserById(id)
                .flatMap(userDto -> ServerResponse.ok().bodyValue(userDto))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createUser(final ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequest.class)
                .map(this::validateResource)
                .map(userRequestMono -> userRequestMono.map(userMapper::toUserDto))
                .map(userService::createUser)
                .map(userDtoMono -> userDtoMono.map(userMapper::toUserResponse))
                .flatMap(courseResponseDtoMono ->
                        ServerResponse.ok().body(courseResponseDtoMono, UserResponse.class));
    }

    public Mono<ServerResponse> updateUser(final ServerRequest serverRequest) {
        final Long id = Long.valueOf(serverRequest.pathVariable(ID));
        return serverRequest.bodyToMono(UserRequest.class)
                .map(this::validateResource)
                .map(userRequestMono -> userRequestMono.map(userMapper::toUserDto))
                .map(userDtoMono -> userService.updateUser(id, userDtoMono))
                .map(userDtoMono -> userDtoMono.map(userMapper::toUserResponse))
                .flatMap(courseResponseDtoMono ->
                        ServerResponse.ok().body(courseResponseDtoMono, UserResponse.class));
    }

    public Mono<ServerResponse> deleteUser(final ServerRequest serverRequest) {
        final Long id = Long.valueOf(serverRequest.pathVariable(ID));
        return ServerResponse.ok()
                .body(userService.deleteUser(id), Void.class);
    }

    private Mono<UserRequest> validateResource(final UserRequest userRequest) {
        final Errors errors = new BeanPropertyBindingResult(userRequest, "userRequest");
        userValidator.validate(userRequest, errors);
        if (errors.hasErrors()) {
            return Mono.defer(() ->
                    Mono.error(new RequestValidatorException(errors)));
        }
        return Mono.defer(() -> Mono.just(userRequest));
    }

}
