package com.training.subjects.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter
@Setter
public class Subject {

    @Id
    @Column(name = "subject_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectsSequenceGenerator")
    @SequenceGenerator(name = "subjectsSequenceGenerator", sequenceName = "subjects_sequence")
    private Long id;

    @NotEmpty
    private String name;

    @Nullable
    private String description;

    @NotNull
    private Long professorId;
}
