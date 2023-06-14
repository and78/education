package com.training.subjects.listener;

import com.training.common.event.CourseCommandEvent;
import com.training.common.mapper.JsonMapper;
import com.training.common.mapper.JsonMapperImpl;
import com.training.subjects.domain.Subject;
import com.training.subjects.mapper.SubjectMapper;
import com.training.subjects.mapper.SubjectMapperImpl;
import com.training.subjects.repository.SubjectRepository;
import com.training.subjects.service.EventProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumerListenerTest {


    @InjectMocks
    private ConsumerListener consumerListener;

    @Mock
    private SubjectRepository subjectRepository;

    @Spy
    private JsonMapper jsonMapper = new JsonMapperImpl();

    @Mock
    private EventProducerService eventProducerService;

    @Spy
    private SubjectMapper subjectMapper = new SubjectMapperImpl();

    @Test
    void shouldPerformListener() {
        String id = "Course 1";
        long subjectId = 100L;

        CourseCommandEvent courseCommandEvent = new CourseCommandEvent();
        courseCommandEvent.setCourseId(id);
        courseCommandEvent.setSubjectIds(List.of(subjectId));

        Subject subject = new Subject();
        subject.setId(subjectId);
        subject.setName("Subject 1");
        subject.setDescription("Description of subject 1");
        subject.setProfessorName("Professor 1");

        when(subjectRepository.findAllById(courseCommandEvent.getSubjectIds())).thenReturn(List.of(subject));

        consumerListener.listener(jsonMapper.fromObjectToJson(courseCommandEvent));

        verify(subjectRepository, times(1)).findAllById(courseCommandEvent.getSubjectIds());
        verify(eventProducerService, times(1)).sendMessageToCoursesService(anyString());
    }

}