package com.litmus7.Streakify.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabitDTO {

    private Long id;
    private String name;
    private int targetDaysPerWeek;
}