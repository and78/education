package com.training.subjects.service;

import com.training.subjects.common.SubjectUpdatedEvent;
import com.training.subjects.domain.Subject;
import com.training.subjects.dto.SubjectDto;
import com.training.subjects.exception.SubjectNotFoundException;
import com.training.subjects.mapper.JsonMapper;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final EventProducerService eventProducerService;
    private final JsonMapper jsonMapper;

    @Override
    public List<SubjectDto> findAll(int page, int size) {
        return subjectRepository.findAll(PageRequest.of(page, size))
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
    public SubjectDto save(final SubjectDto dto) {
        final Function<SubjectDto, Subject> toSubject = subjectMapper::toEntity;
        return toSubject
                .andThen(subjectRepository::save)
                .andThen(subjectMapper::toDto)
                .apply(dto);
    }

    @Override
    public SubjectDto update(final SubjectDto dto) {
        final Function<SubjectDto, Subject> toSubject = subjectMapper::toEntity;
        return toSubject
                .andThen(subjectRepository::save)
                .andThen(subjectMapper::toDto)
                .andThen(subjectDto -> {
                    sendSubjectUpdatedEvent(subjectDto);
                    return subjectDto;
                })
                .apply(dto);
    }

    private void sendSubjectUpdatedEvent(final SubjectDto subjectDto) {
        final SubjectUpdatedEvent subjectUpdatedEvent = new SubjectUpdatedEvent(subjectDto);
        final String json = jsonMapper.fromObjectToJson(subjectUpdatedEvent);
        eventProducerService.sendMessageToCoursesServiceSubjectUpdated(json);
    }

}
