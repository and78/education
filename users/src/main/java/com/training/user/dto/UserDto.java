package com.training.user.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private short userTypeId;

}
