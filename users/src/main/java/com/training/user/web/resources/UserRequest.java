package com.training.user.web.resources;

import com.training.user.enums.UserType;
import com.training.user.validation.EnumValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
        @NotEmpty(message = "name must not be empty")
        String name,

        @NotEmpty(message = "lastName must not be empty")
        String lastName,

        @NotEmpty(message = "email must not be empty")
        @Email(message = "email must be a well-formed email address")
        String email,

        @EnumValue(value = UserType.class, message = "userTypeId must be a valid id")
        short userTypeId) {
}
