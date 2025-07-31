package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.challenge.school.domain.School;
import com.challenge.school.dto.SchoolCreateDto;
import com.challenge.school.dto.SchoolDto;
import com.challenge.school.dto.SchoolUpdateDto;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

    SchoolDto toDto(School entity);
    @Mapping(target = "students", ignore = true)
    SchoolDto toDtoWithoutStudents(School entity);
    
    School toEntity(SchoolCreateDto dto);

    void updateFromDto(SchoolUpdateDto dto, @MappingTarget School entity);
}
