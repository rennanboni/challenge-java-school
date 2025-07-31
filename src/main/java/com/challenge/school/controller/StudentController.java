package com.challenge.school.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.school.domain.Student;
import com.challenge.school.domain.specification.StudentSpecification;
import com.challenge.school.dto.StudentCreateDto;
import com.challenge.school.dto.StudentDto;
import com.challenge.school.dto.StudentUpdateDto;
import com.challenge.school.mapper.StudentMapper;
import com.challenge.school.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schools/{schoolId}/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public Page<StudentDto> listStudents(
            @PathVariable Long schoolId,
    		@RequestParam(required = false) String name,
    		@RequestParam(required = false) String nameLike,

    		@RequestParam(defaultValue = "0") int page,
    		@RequestParam(defaultValue = "10") int size,
    		@RequestParam(defaultValue = "id,asc") String[] sorts
    		) {

    	// pagination
    	Sort sort = Sort.by(Sort.Direction.fromString(sorts[1]), sorts[0]);
    	Pageable pageable = PageRequest.of(page,  size, sort);

    	// filters
    	Specification<Student> spec = Specification
                .where(StudentSpecification.hasSchoolId(schoolId))
    			.and(StudentSpecification.hasName(name))
    			.and(StudentSpecification.hasNameLike(nameLike));

    	// search
        Page<Student> schools = studentService.findAll(spec, pageable);
        return schools.map(this.studentMapper::toDto);
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable Long id) {
        return this.studentMapper.toDto(studentService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@PathVariable Long schoolId, @Valid @RequestBody StudentCreateDto dto) {
        dto.setSchoolId(schoolId);
        Student student = studentService.create(dto);
        return this.studentMapper.toDto(student);
    }

    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Long schoolId, @PathVariable Long id, @Valid @RequestBody StudentUpdateDto dto) {
        if (dto.getSchoolId() == null) dto.setSchoolId(schoolId);
        Student student = studentService.update(id, dto);
        return this.studentMapper.toDto(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long schoolId, @PathVariable Long id) {
    	this.studentService.delete(schoolId, id);
    }
}
