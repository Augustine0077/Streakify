package com.litmus7.Streakify.dto;

import lombok.Data;
import java.util.List;

@Data
public class DashboardDTO {

    private int totalHabits;
    private int activeHabits;
    private int completedToday;

    private List<HabitStreakDTO> currentStreaks;

    private int consistencyScore;
}