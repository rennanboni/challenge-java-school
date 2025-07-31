package com.challenge.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.challenge.school.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
//    Page<Student> findBySchoolIdAndNameContainingIgnoreCase(Long schoolId, String name, Pageable pageable);
}
