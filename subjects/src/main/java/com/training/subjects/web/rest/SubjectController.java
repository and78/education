package com.training.subjects.web.rest;

import com.training.subjects.service.SubjectWrapperService;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class SubjectController {

    private final SubjectWrapperService subjectWrapperService;

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectWrapperService.findAll());
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(subjectWrapperService.findById(id));
    }

    @PostMapping("/subjects")
    public ResponseEntity<String> createSubject(HttpServletRequest httpRequest, @Valid @RequestBody final SubjectRequest request) {
        final String subjectId = subjectWrapperService.save(request).toString();
        return ResponseEntity.created(
                URI.create(String.join("/", httpRequest.getRequestURL().toString(), subjectId))
        ).build();
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<SubjectResponse> deleteSubjectById(@PathVariable(name = "id") Long id) {
        subjectWrapperService.deleteById(id);
        return ResponseEntity.accepted().build();
    }
}
