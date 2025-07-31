package com.challenge.school.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.challenge.school.domain.Student;
import com.challenge.school.dto.CreateStudentDto;
import com.challenge.school.dto.StudentDto;
import com.challenge.school.dto.UpdateStudentDto;
import com.challenge.school.mapper.StudentMapper;
import com.challenge.school.service.StudentService;
import org.mapstruct.factory.Mappers;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping
    public Page<StudentDto> listStudents(@RequestParam Long schoolId, @RequestParam(required = false) String name, Pageable pageable) {
        Page<Student> students = studentService.listStudents(schoolId, name, pageable);
        return students.map(studentMapper::toDto);
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable Long id) {
        return studentMapper.toDto(studentService.getStudent(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto createStudent(@Valid @RequestBody CreateStudentDto dto) {
        Student student = studentService.createStudent(dto);
        return studentMapper.toDto(student);
    }

    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentDto dto) {
        Student student = studentService.updateStudent(id, dto);
        return studentMapper.toDto(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
