package com.training.subjects.web.rest;

import com.training.subjects.service.SubjectWrapperService;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectWrapperService subjectWrapperService;

    @Test
    void shouldGetAllSubjects() {
        long id = 100L;

        SubjectResponse subjectResponse = new SubjectResponse(
                id,
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        when(subjectWrapperService.findAll(anyInt(), anyInt())).thenReturn(List.of(subjectResponse));

        ResponseEntity<List<SubjectResponse>> allSubjects = subjectController.getAllSubjects(1, 10);


        assertThat(allSubjects.getBody(), is(notNullValue()));
        assertThat(allSubjects.getBody().stream().allMatch(response ->
                response.id().equals(subjectResponse.id())
                        && response.name().equals(subjectResponse.name())
                        && response.description().equals(subjectResponse.description())
                        && response.professorName().equals(subjectResponse.professorName())), is(true));

        verify(subjectWrapperService, times(1)).findAll(anyInt(), anyInt());

    }

    @Test
    void shouldGetSubjectById() {
        long id = 100L;

        SubjectResponse subjectResponse = new SubjectResponse(
                id,
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        when(subjectWrapperService.findById(anyLong())).thenReturn(subjectResponse);

        ResponseEntity<SubjectResponse> subject = subjectController.getSubjectById(1L);

        SubjectResponse body = subject.getBody();

        assertThat(body, is(notNullValue()));
        assertThat(body.id(), is(subjectResponse.id()));
        assertThat(body.name(), is(subjectResponse.name()));
        assertThat(body.description(), is(subjectResponse.description()));
        assertThat(body.professorName(), is(subjectResponse.professorName()));

        verify(subjectWrapperService, times(1)).findById(anyLong());

    }

    @Test
    void shouldCreateSubject() {
        long id = 100L;

        SubjectRequest subjectRequest = new SubjectRequest(
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        SubjectResponse subjectResponse = new SubjectResponse(
                id,
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        when(subjectWrapperService.save(any(SubjectRequest.class))).thenReturn(subjectResponse);

        ResponseEntity<SubjectResponse> subject = subjectController.createSubject(null, subjectRequest);

        SubjectResponse body = subject.getBody();

        assertThat(body, is(notNullValue()));
        assertThat(body.id(), is(subjectResponse.id()));
        assertThat(body.name(), is(subjectResponse.name()));
        assertThat(body.description(), is(subjectResponse.description()));
        assertThat(body.professorName(), is(subjectResponse.professorName()));

        verify(subjectWrapperService, times(1)).save(any(SubjectRequest.class));

    }

    @Test
    void shouldUpdateSubject() {
        long id = 100L;

        SubjectRequest subjectRequest = new SubjectRequest(
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        SubjectResponse subjectResponse = new SubjectResponse(
                id,
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        when(subjectWrapperService.update(anyLong(), any(SubjectRequest.class))).thenReturn(subjectResponse);

        ResponseEntity<SubjectResponse> subject = subjectController.updateSubject(null, id, subjectRequest);

        SubjectResponse body = subject.getBody();

        assertThat(body, is(notNullValue()));
        assertThat(body.id(), is(subjectResponse.id()));
        assertThat(body.name(), is(subjectResponse.name()));
        assertThat(body.description(), is(subjectResponse.description()));
        assertThat(body.professorName(), is(subjectResponse.professorName()));

        verify(subjectWrapperService, times(1)).update(anyLong(), any(SubjectRequest.class));

    }
}