package com.challenge.school.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateSchoolDto {
    private String name;

    @Min(50)
    @Max(2000)
    private Integer maxCapacity;
}
