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

import com.challenge.school.domain.School;
import com.challenge.school.domain.specification.SchoolSpecification;
import com.challenge.school.dto.SchoolCreateDto;
import com.challenge.school.dto.SchoolDto;
import com.challenge.school.dto.SchoolUpdateDto;
import com.challenge.school.mapper.SchoolMapper;
import com.challenge.school.service.SchoolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schools")
@RequiredArgsConstructor
public class SchoolController {

	private final SchoolService schoolService;
	private final SchoolMapper schoolMapper;

    @GetMapping
    public Page<SchoolDto> findAll(
		@RequestParam(required = false) String name,
		@RequestParam(required = false) String nameLike,
		
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id,asc") String[] sorts) {
    	
    	// pagination
		Sort sort = Sort.by(Sort.Direction.fromString(sorts[1]), sorts[0]);
		Pageable pageable = PageRequest.of(page,  size, sort);
		
		// filters
		Specification<School> spec = Specification
				.where(SchoolSpecification.hasName(name))
				.and(SchoolSpecification.hasNameLike(nameLike));
		
		// search
	    Page<School> schools = schoolService.findAll(spec, pageable);
	    return schools.map(schoolMapper::toDto);
    }

    @GetMapping("/{id}")
    public SchoolDto findById(@PathVariable Long id) {
        return this.schoolMapper.toDto(schoolService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolDto create(@Valid @RequestBody SchoolCreateDto dto) {
        School school = schoolService.create(dto);
        return this.schoolMapper.toDto(school);
    }

    @PutMapping("/{id}")
    public SchoolDto update(@PathVariable Long id, @Valid @RequestBody SchoolUpdateDto dto) {
        School school = schoolService.update(id, dto);
        return this.schoolMapper.toDto(school);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
    	this.schoolService.delete(id);
    }
}
