package com.training.user.web.resources;

public record UserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        UserTypeResponse userType) {
}
