package com.litmus7.Streakify.dto;

import jakarta.validation.constraints.Max;     // ⭐ ADDED
import jakarta.validation.constraints.Min;     // ⭐ ADDED
import jakarta.validation.constraints.NotNull; // ⭐ ADDED
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitRequestDTO {

    private String name;

    // ⭐ UPDATED: validation added here
    @NotNull(message = "Target days are mandatory")
    @Min(value = 1, message = "Target must be at least 1 day")
    @Max(value = 7, message = "Target can't be greater than 7")
    private Integer targetDaysPerWeek; // ⭐ changed int → Integer

    private Long userId;
}