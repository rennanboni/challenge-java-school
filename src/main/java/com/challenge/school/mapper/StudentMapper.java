package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.challenge.school.domain.Student;
import com.challenge.school.dto.CreateStudentDto;
import com.challenge.school.dto.StudentDto;
import com.challenge.school.dto.UpdateStudentDto;
import com.challenge.school.repository.SchoolRepository;

@Mapper(componentModel = "spring", uses = { SchoolRepository.class })
public abstract class StudentMapper {

	@Mapping(source = "school.id", target = "schoolId")
	public abstract StudentDto toDto(Student entity);

    @Mapping(source = "schoolId", target = "school.id")
    public abstract Student toEntity(CreateStudentDto dto);

    public abstract void updateFromDto(UpdateStudentDto dto, @MappingTarget Student entity);
}
