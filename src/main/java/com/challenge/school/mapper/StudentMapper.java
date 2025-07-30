package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.challenge.school.domain.Student;
import com.challenge.school.dto.StudentDto;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Mapping(source = "school.id", target = "schoolId")
    StudentDto toDto(Student student);
}
