package com.challenge.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.school.domain.School;
import com.challenge.school.domain.Student;
import com.challenge.school.dto.CreateStudentDto;
import com.challenge.school.dto.UpdateStudentDto;
import com.challenge.school.exception.ResourceNotFoundException;
import com.challenge.school.exception.SchoolAtMaxCapacityException;
import com.challenge.school.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final SchoolService schoolService;
	
    @Transactional(readOnly = true)
    public Page<Student> findAll(Specification<Student> spec, Pageable pageable) {
    	return studentRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public Student create(CreateStudentDto dto) {
        School school = schoolService.findById(dto.getSchoolId());

        if (school.getStudents().size() >= school.getMaxCapacity()) {
            throw new SchoolAtMaxCapacityException("School with id " + dto.getSchoolId() + " is at maximum capacity");
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setSchool(school);
        return studentRepository.save(student);
    }

    @Transactional
    public Student update(Long id, UpdateStudentDto dto) {
        Student student = findById(id);

        if (dto.getName() != null) {
            student.setName(dto.getName());
        }

        if (dto.getSchoolId() != null && !dto.getSchoolId().equals(student.getSchool().getId())) {
            School school = schoolService.findById(dto.getSchoolId());
            if (school.getStudents().size() >= school.getMaxCapacity()) {
                throw new SchoolAtMaxCapacityException("School with id " + dto.getSchoolId() + " is at maximum capacity");
            }
            student.setSchool(school);
        }

        return studentRepository.save(student);
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.delete(findById(id));
    }
}
