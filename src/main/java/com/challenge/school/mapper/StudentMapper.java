package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.challenge.school.domain.Student;
import com.challenge.school.dto.StudentCreateDto;
import com.challenge.school.dto.StudentDto;
import com.challenge.school.dto.StudentUpdateDto;
import com.challenge.school.repository.SchoolRepository;

@Mapper(componentModel = "spring", uses = { SchoolRepository.class })
public abstract class StudentMapper {

	@Mapping(source = "school.id", target = "schoolId")
	public abstract StudentDto toDto(Student entity);

    @Mapping(source = "schoolId", target = "school.id")
    public abstract Student toEntity(StudentCreateDto dto);

    public abstract void updateFromDto(StudentUpdateDto dto, @MappingTarget Student entity);
}
