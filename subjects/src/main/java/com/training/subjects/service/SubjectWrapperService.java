package com.training.subjects.service;

import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;

import java.util.List;

public interface SubjectWrapperService {

    List<SubjectResponse> findAll(int page, int size);

    SubjectResponse findById(Long id);

    SubjectResponse save(SubjectRequest request);

    SubjectResponse update(Long id, SubjectRequest request);

}
