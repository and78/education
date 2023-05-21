package com.training.user.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "user_types")
@Data
@Immutable
public class UserType {

    @Id
    @Column(value = "user_type_id")
    private Short id;

    private String description;
}
