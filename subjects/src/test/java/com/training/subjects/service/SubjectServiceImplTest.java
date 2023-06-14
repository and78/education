package com.training.subjects.service;

import com.training.common.mapper.JsonMapper;
import com.training.common.mapper.JsonMapperImpl;
import com.training.subjects.domain.Subject;
import com.training.subjects.dto.SubjectDto;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.mapper.SubjectMapperImpl;
import com.training.subjects.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    private SubjectService subjectService;

    @Mock
    private SubjectRepository subjectRepository;

    @Spy
    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @Mock
    private EventProducerService eventProducerService;

    @Spy
    private JsonMapper jsonMapper = new JsonMapperImpl();

    @BeforeEach
    void setUp() {
        subjectService =
                new SubjectServiceImpl(subjectRepository, subjectMapper, eventProducerService, jsonMapper);
    }

    @Test
    void shouldFindAll() {
        when(subjectRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());

        List<SubjectDto> subjectDtoList = subjectService.findAll(1, 10);

        assertThat(subjectDtoList.isEmpty(), is(true));

        verify(subjectRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void shouldFindById() {
        long id = 100L;

        Subject subject = new Subject();
        subject.setId(id);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");

        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        SubjectDto subjectDto = subjectService.findById(id);

        assertThat(subjectDto.getId(), is(subject.getId()));
        assertThat(subjectDto.getName(), is(subject.getName()));
        assertThat(subjectDto.getDescription(), is(subject.getDescription()));
        assertThat(subjectDto.getProfessorName(), is(subject.getProfessorName()));

        verify(subjectRepository, times(1)).findById(id);

    }

    @Test
    void shouldSave() {
        long id = 100L;

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        Subject subject = new Subject();
        subject.setId(id);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectDto dto = subjectService.save(subjectDto);

        assertThat(dto.getId(), is(subjectDto.getId()));
        assertThat(dto.getName(), is(subjectDto.getName()));
        assertThat(dto.getDescription(), is(subjectDto.getDescription()));
        assertThat(dto.getProfessorName(), is(subjectDto.getProfessorName()));

        verify(subjectRepository, times(1)).save(any(Subject.class));

    }

    @Test
    void shouldUpdate() {
        long id = 100L;

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        Subject subject = new Subject();
        subject.setId(id);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectDto dto = subjectService.update(subjectDto);

        assertThat(dto.getId(), is(subjectDto.getId()));
        assertThat(dto.getName(), is(subjectDto.getName()));
        assertThat(dto.getDescription(), is(subjectDto.getDescription()));
        assertThat(dto.getProfessorName(), is(subjectDto.getProfessorName()));

        verify(subjectRepository, times(1)).save(any(Subject.class));

    }
}