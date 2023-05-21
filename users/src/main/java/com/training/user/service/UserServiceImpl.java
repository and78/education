package com.training.user.service;

import com.training.user.dto.UserDto;
import com.training.user.exception.UserAlreadyExistsException;
import com.training.user.exception.UserNotSavedException;
import com.training.user.mapper.UserMapper;
import com.training.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Flux<UserDto> findAll() {
        return this.userRepository.findAll()
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> findUserById(Long id) {
        return this.userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> createUser(Mono<UserDto> userDtoMono) {
        return userDtoMono.map(userMapper::toEntity)
                .flatMap(userRepository::save)
                .map(userMapper::toDto)
                .onErrorResume(DuplicateKeyException.class,
                        throwable -> Mono.defer(() -> Mono.error(new UserAlreadyExistsException())))
                .onErrorResume(DataAccessException.class,
                        throwable -> Mono.defer(() -> Mono.error(new UserNotSavedException())));
    }

    @Override
    public Mono<UserDto> updateUser(Long id, Mono<UserDto> userDtoMono) {
        return userRepository.findById(id)
                .flatMap(course -> userDtoMono.map(userMapper::toEntity)
                        .doOnNext(entity -> entity.setId(id)))
                .flatMap(userRepository::save)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}
