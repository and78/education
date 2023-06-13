package com.training.subjects.web.rest;

import com.training.subjects.service.SubjectWrapperService;
import com.training.subjects.web.resources.SubjectRequest;
import com.training.subjects.web.resources.SubjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class SubjectController {

    private final SubjectWrapperService subjectWrapperService;

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects(
            @RequestParam(value = "page", defaultValue = "0")
            @PositiveOrZero(message = "page must be grater than or equal to zero") int page,
            @RequestParam(value = "size", defaultValue = "10")
            @Positive(message = "size must be grater than zero") int size) {
        return ResponseEntity.ok(subjectWrapperService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(subjectWrapperService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubjectResponse> createSubject(HttpServletRequest httpRequest, @Valid @RequestBody final SubjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectWrapperService.save(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(HttpServletRequest httpRequest,
                                                         @PathVariable(name = "id") Long id,
                                                         @Valid @RequestBody final SubjectRequest request) {
        return ResponseEntity.ok(subjectWrapperService.update(id, request));
    }

}
