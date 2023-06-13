package com.training.subjects.repository;

import com.training.subjects.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long>, PagingAndSortingRepository<Subject, Long> {
}
