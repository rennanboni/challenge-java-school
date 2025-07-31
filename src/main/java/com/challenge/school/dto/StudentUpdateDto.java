package com.challenge.school.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateDto {
    private String name;
    @Schema(hidden = true)
    private Long schoolId;
}
