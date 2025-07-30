package com.challenge.school.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.school.domain.School;
import com.challenge.school.domain.Student;
import com.challenge.school.dto.CreateStudentDto;
import com.challenge.school.dto.UpdateStudentDto;
import com.challenge.school.exception.ResourceNotFoundException;
import com.challenge.school.exception.SchoolAtMaxCapacityException;
import com.challenge.school.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final SchoolService schoolService;
	
    @Transactional(readOnly = true)
    public Page<Student> listStudents(Long schoolId, String name, Pageable pageable) {
        if (name != null) {
            return studentRepository.findBySchoolIdAndNameContainingIgnoreCase(schoolId, name, pageable);
        } else {
            return studentRepository.findAll(pageable);
        }
    }

    @Transactional(readOnly = true)
    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public Student createStudent(CreateStudentDto dto) {
        School school = schoolService.getSchool(dto.getSchoolId());

        if (school.getStudents().size() >= school.getMaxCapacity()) {
            throw new SchoolAtMaxCapacityException("School with id " + dto.getSchoolId() + " is at maximum capacity");
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setSchool(school);
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Long id, UpdateStudentDto dto) {
        Student student = getStudent(id);

        if (dto.getName() != null) {
            student.setName(dto.getName());
        }

        if (dto.getSchoolId() != null && !dto.getSchoolId().equals(student.getSchool().getId())) {
            School school = schoolService.getSchool(dto.getSchoolId());
            if (school.getStudents().size() >= school.getMaxCapacity()) {
                throw new SchoolAtMaxCapacityException("School with id " + dto.getSchoolId() + " is at maximum capacity");
            }
            student.setSchool(school);
        }

        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.delete(getStudent(id));
    }
}
