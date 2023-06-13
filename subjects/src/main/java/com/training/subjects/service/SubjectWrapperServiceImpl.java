package com.training.subjects.service;

import com.training.subjects.dto.SubjectDto;
import com.training.subjects.exception.SubjectNotFoundException;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
class SubjectWrapperServiceImpl implements SubjectWrapperService {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper;

    @Override
    public List<SubjectResponse> findAll(final int page, final int size) {
        return subjectService.findAll(page, size)
                .stream()
                .map(subjectMapper::toResponse)
                .toList();
    }

    @Override
    public SubjectResponse findById(final Long id) {
        return Optional.ofNullable(subjectService.findById(id))
                .map(subjectMapper::toResponse)
                .orElseThrow(SubjectNotFoundException::new);
    }

    @Override
    public SubjectResponse save(final SubjectRequest request) {
        final Function<SubjectRequest, SubjectDto> toSubject = subjectMapper::toDto;
        return toSubject
                .andThen(subjectService::save)
                .andThen(subjectMapper::toResponse)
                .apply(request);
    }

    @Override
    public SubjectResponse update(final Long id, final SubjectRequest request) {
        final Function<SubjectRequest, SubjectDto> toSubject = subjectMapper::toDto;
        return toSubject
                .andThen(subjectDto -> {
                    subjectDto.setId(id);
                    return subjectDto;
                })
                .andThen(subjectService::update)
                .andThen(subjectMapper::toResponse)
                .apply(request);
    }

}
