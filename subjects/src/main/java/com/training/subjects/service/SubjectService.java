package com.training.subjects.service;

import com.training.subjects.dto.SubjectDto;

import java.util.List;

interface SubjectService {

    List<SubjectDto> findAll();

    SubjectDto findById(Long id);

    Long save(SubjectDto request);

    void deleteById(Long id);

}
