package com.challenge.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.challenge.school.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findBySchoolIdAndNameContainingIgnoreCase(Long schoolId, String name, Pageable pageable);
}
