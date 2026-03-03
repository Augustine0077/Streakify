package com.litmus7.Streakify.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HabitLogRequestDTO {

    private LocalDate logDate;
    private boolean completed;
}