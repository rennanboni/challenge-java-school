package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.challenge.school.domain.Student;
import com.challenge.school.dto.StudentCreateDto;
import com.challenge.school.dto.StudentDto;
import com.challenge.school.dto.StudentUpdateDto;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	@Mapping(source = "school.id", target = "schoolId")
	StudentDto toDto(Student entity);

    @Mapping(source = "schoolId", target = "school.id")
    Student toEntity(StudentCreateDto dto);

    void updateFromDto(StudentUpdateDto dto, @MappingTarget Student entity);
}
