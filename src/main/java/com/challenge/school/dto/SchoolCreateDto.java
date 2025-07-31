package com.challenge.school.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SchoolCreateDto {
    @NotBlank
    private String name;

    @Min(value = 50, message = "Capacity should be at least 50")
    @Max(value = 2000, message = "Capacity should be maximum 2000")
    private Integer maxCapacity;
}
