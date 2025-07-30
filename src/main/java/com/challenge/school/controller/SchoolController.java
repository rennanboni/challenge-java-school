package com.challenge.school.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.challenge.school.domain.School;
import com.challenge.school.dto.CreateSchoolDto;
import com.challenge.school.dto.SchoolDto;
import com.challenge.school.dto.UpdateSchoolDto;
import com.challenge.school.mapper.SchoolMapper;
import com.challenge.school.service.SchoolService;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {
	
	private final SchoolService schoolService;
    private final SchoolMapper schoolMapper;

    @GetMapping
    public Page<SchoolDto> listSchools(@RequestParam(required = false) String name, Pageable pageable) {
        Page<School> schools = schoolService.listSchools(name, pageable);
        return schools.map(schoolMapper::toDto);
    }

    @GetMapping("/{id}")
    public SchoolDto getSchool(@PathVariable Long id) {
        return schoolMapper.toDto(schoolService.getSchool(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto createSchool(@Valid @RequestBody CreateSchoolDto dto) {
        School school = schoolService.createSchool(dto);
        return schoolMapper.toDto(school);
    }

    @PutMapping("/{id}")
    public SchoolDto updateSchool(@PathVariable Long id, @Valid @RequestBody UpdateSchoolDto dto) {
        School school = schoolService.updateSchool(id, dto);
        return schoolMapper.toDto(school);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
    }
}
