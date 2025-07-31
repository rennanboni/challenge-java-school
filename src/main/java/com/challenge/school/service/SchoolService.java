package com.challenge.school.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.school.domain.School;
import com.challenge.school.domain.specification.SchoolSpecification;
import com.challenge.school.dto.CreateSchoolDto;
import com.challenge.school.dto.UpdateSchoolDto;
import com.challenge.school.exception.DuplicateResourceException;
import com.challenge.school.exception.ResourceNotFoundException;
import com.challenge.school.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolService {
	private final SchoolRepository schoolRepository;
	
    @Transactional(readOnly = true)
    public Page<School> findAll(Specification<School> spec, Pageable pageable) {
        return schoolRepository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    public School findById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
    }

    @Transactional
    public School create(CreateSchoolDto dto) {
        schoolRepository.findByName(dto.getName()).ifPresent(s -> {
            throw new DuplicateResourceException("School with name " + dto.getName() + " already exists");
        });

        School school = new School();
        school.setName(dto.getName());
        school.setMaxCapacity(dto.getMaxCapacity());
        return schoolRepository.save(school);
    }

    @Transactional
    public School update(Long id, UpdateSchoolDto dto) {
        School school = findById(id);

        if (dto.getName() != null) {
            schoolRepository.findByName(dto.getName()).ifPresent(s -> {
                if (!s.getId().equals(id)) {
                    throw new DuplicateResourceException("School with name " + dto.getName() + " already exists");
                }
            });
            school.setName(dto.getName());
        }

        if (dto.getMaxCapacity() != null) {
            school.setMaxCapacity(dto.getMaxCapacity());
        }

        return schoolRepository.save(school);
    }

    @Transactional
    public void delete(Long id) {
        schoolRepository.delete(Specification.where(SchoolSpecification.byId(id)));
    }
}
