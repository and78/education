package com.training.common.mapper;

import com.training.common.event.CourseCommandEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
class JsonMapperTest {

    private JsonMapper jsonMapper = new JsonMapperImpl();

    @Test
    void fromObjectToJson() {
        String courseId = "Course 1";
        long subjectId = 1L;

        CourseCommandEvent courseCommandEvent = new CourseCommandEvent();
        courseCommandEvent.setCourseId(courseId);
        courseCommandEvent.setSubjectIds(List.of(subjectId));

        String json = jsonMapper.fromObjectToJson(courseCommandEvent);

        assertThat(json.contains(courseId), is(true));

    }

    @Test
    void fromJsonToObject() {
        String courseId = "Course 1";
        long subjectId = 1L;
        String json = "{\"courseId\":\"" + courseId + "\",\"subjectIds\":[" + subjectId + "]}";

        CourseCommandEvent courseCommandEvent = jsonMapper.fromJsonToObject(json, CourseCommandEvent.class);

        assertThat(courseCommandEvent.getCourseId(), is(courseId));
        assertThat(courseCommandEvent.getSubjectIds().stream().findFirst().orElseThrow(), is(subjectId));
    }
}