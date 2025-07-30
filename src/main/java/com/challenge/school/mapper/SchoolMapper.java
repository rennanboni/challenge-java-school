package com.challenge.school.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.challenge.school.domain.School;
import com.challenge.school.dto.SchoolDto;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    SchoolDto toDto(School school);
}
