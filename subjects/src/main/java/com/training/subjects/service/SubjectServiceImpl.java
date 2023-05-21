package com.training.subjects.service;

import com.training.subjects.domain.Subject;
import com.training.subjects.dto.SubjectDto;
import com.training.subjects.exception.SubjectNotFoundException;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public List<SubjectDto> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toDto)
                .toList();
    }

    @Override
    public SubjectDto findById(final Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toDto)
                .orElseThrow(SubjectNotFoundException::new);
    }

    @Override
    public Long save(final SubjectDto request) {
        final Function<SubjectDto, Subject> toSubject = subjectMapper::toEntity;
        return toSubject
                .andThen(subjectRepository::save)
                .andThen(Subject::getId)
                .apply(request);
    }

    @Override
    public void deleteById(Long id) {
        subjectRepository.deleteById(id);
    }

}
