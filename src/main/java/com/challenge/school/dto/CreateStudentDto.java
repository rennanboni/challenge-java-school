package com.challenge.school.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateStudentDto {
    @NotBlank
    private String name;

    @NotNull
    private Long schoolId;
}
