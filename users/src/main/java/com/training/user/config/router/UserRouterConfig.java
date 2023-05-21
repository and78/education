package com.training.user.config.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.training.user.config.Constants.EMPTY;
import static com.training.user.config.Constants.ID;

@Configuration
@RequiredArgsConstructor
public class UserRouterConfig {

    private final UserRouterHandler userRouterHandler;

    @Bean
    public RouterFunction<ServerResponse> baseMappingQueryRouter() {
        return RouterFunctions.route()
                .path("api/users", this::routerMappings)
                .build();
    }

    public RouterFunction<ServerResponse> routerMappings() {
        return RouterFunctions.route()
                .GET(EMPTY, userRouterHandler::findAllUsers)
                .GET("/{" + ID + "}", userRouterHandler::findUserById)
                .POST(EMPTY, userRouterHandler::createUser)
                .PUT("/{" + ID + "}", userRouterHandler::updateUser)
                .DELETE("/{" + ID + "}", userRouterHandler::deleteUser)
                .build();
    }

}
