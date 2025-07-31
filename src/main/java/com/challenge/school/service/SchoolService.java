package com.challenge.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.challenge.school.domain.School;
import com.challenge.school.domain.specification.SchoolSpecification;
import com.challenge.school.dto.SchoolCreateDto;
import com.challenge.school.dto.SchoolUpdateDto;
import com.challenge.school.exception.DuplicateResourceException;
import com.challenge.school.exception.ResourceNotFoundException;
import com.challenge.school.mapper.SchoolMapper;
import com.challenge.school.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolService {
	private final SchoolRepository schoolRepository;
	private final SchoolMapper schoolMapper;
	
    public Page<School> findAll(Specification<School> spec, Pageable pageable) {
        return this.schoolRepository.findAll(spec, pageable);
    }

    public School findById(Long id) {
        return this.schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
    }

    public School create(SchoolCreateDto dto) {
    	School entity = this.schoolMapper.toEntity(dto);
    	
    	// validation
        validateOrFail(entity);
        
        // save
        return this.schoolRepository.save(entity);
    }

    public School update(Long id, SchoolUpdateDto dto) {
        School entity = this.findById(id);
        schoolMapper.updateFromDto(dto, entity);
        
        // validation
        validateOrFail(entity);

        // save
        return this.schoolRepository.save(entity);
    }

    public void delete(Long id) {
        this.schoolRepository.delete(Specification.where(SchoolSpecification.byId(id)));
    }
    
    private void validateOrFail(School entity) {
    	checkDuplicatedName(entity);
    }
    
    private void checkDuplicatedName(School entity) {
    	if (entity.getId() == null) {
    		int total = this.schoolRepository.countByName(entity.getName());
    		if (total > 0) throw new DuplicateResourceException(String.format("School with name '%s' already exists", entity.getName()));
    	} else {
    		this.schoolRepository.findByName(entity.getName())
    			.ifPresent(_entity -> {
	                if (!_entity.getId().equals(entity.getId())) {
	                    throw new DuplicateResourceException(String.format("School with name '%s' already exists", entity.getName()));
	                }
    			});
    	}
    }
}
