package com.litmus7.Streakify.dto;

import lombok.Data;

@Data
public class HabitStreakDTO {

    private String habitName;
    private int currentStreak;
    private int longestStreak;
}