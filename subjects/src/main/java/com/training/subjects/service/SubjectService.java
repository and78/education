package com.training.subjects.service;

import com.training.subjects.dto.SubjectDto;

import java.util.List;

interface SubjectService {

    List<SubjectDto> findAll(int page, int size);

    SubjectDto findById(Long id);

    SubjectDto save(SubjectDto request);

    SubjectDto update(SubjectDto dto);

}
