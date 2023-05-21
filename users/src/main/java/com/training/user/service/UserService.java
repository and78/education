package com.training.user.service;

import com.training.user.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

    Flux<UserDto> findAll();

    Mono<UserDto> findUserById(Long id);

    Mono<UserDto> createUser(Mono<UserDto> userDtoMono);

    Mono<UserDto> updateUser(Long id, Mono<UserDto> userDtoMono);

    Mono<Void> deleteUser(Long id);

}
