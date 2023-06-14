package com.training.subjects.service;

import com.training.subjects.dto.SubjectDto;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.mapper.SubjectMapperImpl;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectWrapperServiceImplTest {

    private SubjectWrapperService subjectWrapperService;

    @Mock
    private SubjectService subjectService;

    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @BeforeEach
    void setUp() {
        subjectWrapperService = new SubjectWrapperServiceImpl(subjectService, subjectMapper);
    }

    @Test
    void shouldFindAll() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        when(subjectService.findAll(anyInt(), anyInt())).thenReturn(List.of(subjectDto));

        List<SubjectResponse> subjectResponses = subjectWrapperService.findAll(1, 10);

        assertThat(subjectResponses.stream().allMatch(response ->
                response.id().equals(subjectDto.getId())
                        && response.name().equals(subjectDto.getName())
                        && response.description().equals(subjectDto.getDescription())
                        && response.professorName().equals(subjectDto.getProfessorName())), is(true));

        verify(subjectService, times(1)).findAll(anyInt(), anyInt());

    }

    @Test
    void shouldFindById() {
        long id = 100L;

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        when(subjectService.findById(id)).thenReturn(subjectDto);

        SubjectResponse subjectResponse = subjectWrapperService.findById(id);

        assertThat(subjectResponse.id(), is(subjectDto.getId()));
        assertThat(subjectResponse.name(), is(subjectDto.getName()));
        assertThat(subjectResponse.description(), is(subjectDto.getDescription()));
        assertThat(subjectResponse.professorName(), is(subjectDto.getProfessorName()));

        verify(subjectService, times(1)).findById(id);

    }

    @Test
    void shouldSave() {
        long id = 100L;

        SubjectRequest subjectRequest = new SubjectRequest(
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        when(subjectService.save(any(SubjectDto.class))).thenReturn(subjectDto);

        SubjectResponse subjectResponse = subjectWrapperService.save(subjectRequest);

        assertThat(subjectResponse.id(), is(subjectDto.getId()));
        assertThat(subjectResponse.name(), is(subjectDto.getName()));
        assertThat(subjectResponse.description(), is(subjectDto.getDescription()));
        assertThat(subjectResponse.professorName(), is(subjectDto.getProfessorName()));

        verify(subjectService, times(1)).save(any(SubjectDto.class));

    }

    @Test
    void shouldUpdate() {
        long id = 100L;

        SubjectRequest subjectRequest = new SubjectRequest(
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(id);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        when(subjectService.update(any(SubjectDto.class))).thenReturn(subjectDto);

        SubjectResponse subjectResponse = subjectWrapperService.update(id, subjectRequest);

        assertThat(subjectResponse.id(), is(subjectDto.getId()));
        assertThat(subjectResponse.name(), is(subjectDto.getName()));
        assertThat(subjectResponse.description(), is(subjectDto.getDescription()));
        assertThat(subjectResponse.professorName(), is(subjectDto.getProfessorName()));

        verify(subjectService, times(1)).update(any(SubjectDto.class));

    }
}