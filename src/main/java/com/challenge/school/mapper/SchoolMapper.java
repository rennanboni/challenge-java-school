package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.challenge.school.domain.School;
import com.challenge.school.dto.CreateSchoolDto;
import com.challenge.school.dto.SchoolDto;
import com.challenge.school.dto.UpdateSchoolDto;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
	SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    @Mapping(target = "students", ignore = true)
    SchoolDto toDto(School entity);
    
    public abstract School toEntity(CreateSchoolDto dto);

    public abstract void updateFromDto(UpdateSchoolDto dto, @MappingTarget School entity);
}
