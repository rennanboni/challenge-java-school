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
import com.challenge.school.mapper.StudentMapper;
import com.challenge.school.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final SchoolService schoolService;
    private final StudentMapper studentMapper;
	
    @Transactional(readOnly = true)
    public Page<Student> findAll(Specification<Student> spec, Pageable pageable) {
    	return studentRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student create(CreateStudentDto createStudentDto) {
        schoolService.findById(createStudentDto.getSchoolId());
        Student entity = studentMapper.toEntity(createStudentDto);
        
        // validation
        validateOrFail(entity);
        
        // save
        return studentRepository.save(entity);
    }

    public Student update(Long id, UpdateStudentDto updateStudentDto) {
        Student entity = findById(id);
        studentMapper.updateFromDto(updateStudentDto, entity);
        
        // validation
        validateOrFail(entity);

        // save
        return studentRepository.save(entity);
    }

    public void delete(Long id) {
        studentRepository.delete(findById(id));
    }
    
    private void validateOrFail(Student entity) {
    	checkMaxCapacitySchool(entity);
    }
    
    private void checkMaxCapacitySchool(Student entity) {
    	School school = this.schoolService.findById(entity.getSchool().getId());
    	int total = this.studentRepository.countBySchoolId(entity.getSchool().getId());
		if (total + 1 > school.getMaxCapacity()) throw new SchoolAtMaxCapacityException(String.format("School '%s' has reached maximum capacity of '%s'", school.getName(), school.getMaxCapacity())); 
    }
}
