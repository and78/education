package com.training.subjects.mapper;

import com.training.common.event.SubjectCreatedEvent;
import com.training.subjects.domain.Subject;
import com.training.subjects.dto.SubjectDto;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SubjectMapperTest {

    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @Test
    void shouldMapToResponse() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        SubjectResponse subjectResponse = subjectMapper.toResponse(subjectDto);

        assertThat(subjectResponse.id(), is(subjectDto.getId()));
        assertThat(subjectResponse.name(), is(subjectDto.getName()));
        assertThat(subjectResponse.description(), is(subjectDto.getDescription()));
        assertThat(subjectResponse.professorName(), is(subjectDto.getProfessorName()));

    }

    @Test
    void shouldMapToDto() {
        SubjectRequest subjectRequest = new SubjectRequest(
                "Subject 1",
                "Description of subject 1",
                "Professor 1"
        );

        SubjectDto subjectDto = subjectMapper.toDto(subjectRequest);

        assertThat(subjectDto.getId(), is(nullValue()));
        assertThat(subjectDto.getName(), is(subjectRequest.name()));
        assertThat(subjectDto.getDescription(), is(subjectRequest.description()));
        assertThat(subjectDto.getProfessorName(), is(subjectRequest.professorName()));

    }

    @Test
    void shouldMapTToDtoFromSubject() {
        Subject subject = new Subject();
        subject.setId(100L);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");

        SubjectDto subjectDto = subjectMapper.toDto(subject);

        assertThat(subjectDto.getId(), is(subject.getId()));
        assertThat(subjectDto.getName(), is(subject.getName()));
        assertThat(subjectDto.getDescription(), is(subject.getDescription()));
        assertThat(subjectDto.getProfessorName(), is(subject.getProfessorName()));

    }

    @Test
    void shouldMapToEntity() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        Subject subject = subjectMapper.toEntity(subjectDto);

        assertThat(subject.getId(), is(subjectDto.getId()));
        assertThat(subject.getName(), is(subjectDto.getName()));
        assertThat(subject.getDescription(), is(subjectDto.getDescription()));
        assertThat(subject.getProfessorName(), is(subjectDto.getProfessorName()));

    }

    @Test
    void shouldMapToEvent() {
        String courseId = "Course 1";

        Subject subject = new Subject();
        subject.setId(100L);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");

        SubjectCreatedEvent subjectCreatedEvent = subjectMapper.toEvent(courseId, List.of(subject));

        assertThat(subjectCreatedEvent.getCourseId(), is(courseId));
        assertThat(subjectCreatedEvent.getSubjects().stream().allMatch(commonSubjectDto ->
                commonSubjectDto.getId().equals(subject.getId())
                        && commonSubjectDto.getName().equals(subject.getName())
                        && commonSubjectDto.getDescription().equals(subject.getDescription())
                        && commonSubjectDto.getProfessorName().equals(subject.getProfessorName())), is(true));

    }
}