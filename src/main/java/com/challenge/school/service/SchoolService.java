package com.challenge.school.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.school.domain.School;
import com.challenge.school.dto.CreateSchoolDto;
import com.challenge.school.dto.UpdateSchoolDto;
import com.challenge.school.exception.DuplicateResourceException;
import com.challenge.school.exception.ResourceNotFoundException;
import com.challenge.school.repository.SchoolRepository;

@Service
@RequiredArgsConstructor
public class SchoolService {
	private final SchoolRepository schoolRepository;
	
    @Transactional(readOnly = true)
    public Page<School> listSchools(String name, Pageable pageable) {
        if (name != null) {
            return schoolRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return schoolRepository.findAll(pageable);
        }
    }

    @Transactional(readOnly = true)
    public School getSchool(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School not found with id: " + id));
    }

    @Transactional
    public School createSchool(CreateSchoolDto dto) {
        schoolRepository.findByName(dto.getName()).ifPresent(s -> {
            throw new DuplicateResourceException("School with name " + dto.getName() + " already exists");
        });

        School school = new School();
        school.setName(dto.getName());
        school.setMaxCapacity(dto.getMaxCapacity());
        return schoolRepository.save(school);
    }

    @Transactional
    public School updateSchool(Long id, UpdateSchoolDto dto) {
        School school = getSchool(id);

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
    public void deleteSchool(Long id) {
        schoolRepository.delete(getSchool(id));
    }
}
