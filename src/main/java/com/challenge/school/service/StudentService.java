package com.challenge.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.challenge.school.domain.School;
import com.challenge.school.domain.Student;
import com.challenge.school.dto.StudentCreateDto;
import com.challenge.school.dto.StudentUpdateDto;
import com.challenge.school.exception.BadRequestException;
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
	
    public Page<Student> findAll(Specification<Student> spec, Pageable pageable) {
    	return studentRepository.findAll(spec, pageable);
    }

    public Student findById(Long id) {
        return this.studentRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student create(StudentCreateDto dto) {
    	this.schoolService.findById(dto.getSchoolId());
        Student entity = studentMapper.toEntity(dto);
        
        // validation
        validateOrFail(entity);
        
        // save
        return this.studentRepository.save(entity);
    }

    public Student update(Long id, StudentUpdateDto dto) {
        Student entity = this.findById(id);
        this.studentMapper.updateFromDto(dto, entity);
        if (entity.getSchool().getId() != dto.getSchoolId()) entity.setSchool(this.schoolService.findById(dto.getSchoolId()));
        
        // validation
        validateOrFail(entity);

        // save
        return studentRepository.save(entity);
    }
    
    public void delete(Long schoolId, Long id) {
    	Student entity = this.findById(id);
    	if (entity.getSchool().getId() != schoolId) {
    		throw new BadRequestException(String.format("Student '%s' does not belong to school '%s'", entity.getName(), schoolId));
    	}
    	
    	this.studentRepository.delete(entity);
    }

    public void delete(Long id) {
    	this.studentRepository.delete(this.findById(id));
    }
    
    private void validateOrFail(Student entity) {
    	this.checkMaxCapacitySchool(entity);
    }
    
    private void checkMaxCapacitySchool(Student entity) {
    	School school = this.schoolService.findById(entity.getSchool().getId());
    	int total = this.studentRepository.countBySchoolId(entity.getSchool().getId());
		if (total + 1 > school.getMaxCapacity()) throw new SchoolAtMaxCapacityException(String.format("School '%s' has reached maximum capacity of '%s' students", school.getName(), school.getMaxCapacity())); 
    }
}
