package com.training.subjects.service;

import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;

import java.util.List;

public interface SubjectWrapperService {

    List<SubjectResponse> findAll();

    SubjectResponse findById(Long id);

    Long save(SubjectRequest request);

    void deleteById(Long id);

}
