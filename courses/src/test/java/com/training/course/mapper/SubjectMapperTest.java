package com.training.course.mapper;

import com.training.common.dto.CommonSubjectDto;
import com.training.course.domain.Subject;
import com.training.course.dto.SubjectDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class SubjectMapperTest {

    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @Test
    void shouldMapToSubject() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);
        subjectDto.setName("Subject 1");
        subjectDto.setDescription("Description of subject 1");
        subjectDto.setProfessorName("Professor 1");

        Subject subject = subjectMapper.toSubject(subjectDto);

        assertThat(subject.getId(), is(subjectDto.getId()));
        assertThat(subject.getName(), is(subjectDto.getName()));
        assertThat(subject.getDescription(), is(subjectDto.getDescription()));
        assertThat(subject.getProfessorName(), is(subjectDto.getProfessorName()));

    }

    @Test
    void shouldMapToSubjectDto() {
        Subject subject = new Subject();
        subject.setId(100L);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");

        SubjectDto subjectDto = subjectMapper.toSubjectDto(subject);

        assertThat(subjectDto.getId(), is(subject.getId()));
        assertThat(subjectDto.getName(), is(subject.getName()));
        assertThat(subjectDto.getDescription(), is(subject.getDescription()));
        assertThat(subjectDto.getProfessorName(), is(subject.getProfessorName()));
    }

    @Test
    void shouldMapToSubjectDtoFromCommonSubjectDto() {
        CommonSubjectDto commonSubjectDto = new CommonSubjectDto();
        commonSubjectDto.setId(100L);
        commonSubjectDto.setName("Subject 1");
        commonSubjectDto.setDescription("Description of subject 1");
        commonSubjectDto.setProfessorName("Professor 1");

        SubjectDto subjectDto = subjectMapper.toSubjectDto(commonSubjectDto);

        assertThat(commonSubjectDto.getId(), is(subjectDto.getId()));
        assertThat(commonSubjectDto.getName(), is(subjectDto.getName()));
        assertThat(commonSubjectDto.getDescription(), is(subjectDto.getDescription()));
        assertThat(commonSubjectDto.getProfessorName(), is(subjectDto.getProfessorName()));
    }

    @Test
    void shouldMapToSubjectCollection() {
        CommonSubjectDto commonSubjectDto = new CommonSubjectDto();
        commonSubjectDto.setId(100L);
        commonSubjectDto.setName("Subject 1");
        commonSubjectDto.setDescription("Description of subject 1");
        commonSubjectDto.setProfessorName("Professor 1");

        List<Subject> subjects = subjectMapper.toSubjectCollection(List.of(commonSubjectDto));

        assertThat(subjects.stream().allMatch(subject ->
                subject.getId().equals(commonSubjectDto.getId())
                        && subject.getName().equals(commonSubjectDto.getName())
                        && subject.getDescription().equals(commonSubjectDto.getDescription())
                        && subject.getProfessorName().equals(commonSubjectDto.getProfessorName())), is(true));
    }

    @Test
    void shouldMapToSubjectDtoFromLong() {
        long subjectId = 1L;

        SubjectDto subjectDto = subjectMapper.toSubjectDto(subjectId);

        assertThat(subjectDto.getId(), is(subjectId));
    }

    @Test
    void shouldMapToSubjectDtos() {
        long subjectId = 1L;

        List<SubjectDto> subjectDtos = subjectMapper.toSubjectDtos(List.of(subjectId));

        assertThat(subjectDtos.stream().findFirst().orElseThrow().getId(), is(subjectId));
    }

    @Test
    void shouldMapToSubjectIdCollection() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(100L);

        List<Long> longList = subjectMapper.toSubjectIdCollection(List.of(subjectDto));

        assertThat(longList.stream().findFirst().orElseThrow(), is(subjectDto.getId()));
    }

    @Test
    void shouldMapToSubjectId() {
        Subject subject = new Subject();
        subject.setId(100L);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");

        Long subjectId = subjectMapper.toSubjectId(subject);

        assertThat(subjectId, is(subject.getId()));
    }

    @Test
    void shouldMapToSubjectFromLong() {
        long subjectId = 1L;

        Subject subject = subjectMapper.toSubject(subjectId);

        assertThat(subject.getId(), is(subjectId));

    }
}