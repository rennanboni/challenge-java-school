package com.challenge.school.dto;

import lombok.Data;

import java.util.List;

@Data
public class SchoolDto {
    private Long id;
    private String name;
    private Integer maxCapacity;
    private List<StudentDto> students;
}
