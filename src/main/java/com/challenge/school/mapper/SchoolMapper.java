package com.challenge.school.mapper;

import com.challenge.school.domain.School;
import com.challenge.school.dto.SchoolDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
	SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

    @Mapping(target = "students", ignore = true)
    SchoolDto toDto(School school);
}
