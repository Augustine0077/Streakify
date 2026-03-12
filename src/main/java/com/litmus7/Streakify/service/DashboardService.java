package com.litmus7.Streakify.service;

import com.litmus7.Streakify.dto.*;
import com.litmus7.Streakify.entity.Habit;
import com.litmus7.Streakify.entity.HabitLog;
import com.litmus7.Streakify.exception.ResourceNotFoundException;
import com.litmus7.Streakify.repository.HabitLogRepository;
import com.litmus7.Streakify.repository.HabitRepository;
import com.litmus7.Streakify.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;
    private final HabitLogService habitLogService;

    public DashboardService(
            UserRepository userRepository,
            HabitRepository habitRepository,
            HabitLogRepository habitLogRepository,
            HabitLogService habitLogService) {

        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.habitLogRepository = habitLogRepository;
        this.habitLogService = habitLogService;
    }

    public DashboardDTO getDashboard(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        List<Habit> habits = habitRepository.findByUserId(userId);

        DashboardDTO dto = new DashboardDTO();

        dto.setTotalHabits(habits.size());
        dto.setActiveHabits(habits.size());

        // =========================
        // COMPLETED TODAY
        // =========================
        int completedToday = 0;

        for (Habit habit : habits) {

            if (habitLogRepository
                    .findByHabitIdAndLogDate(
                            habit.getId(),
                            LocalDate.now())
                    .map(HabitLog::isCompleted)
                    .orElse(false)) {

                completedToday++;
            }
        }

        dto.setCompletedToday(completedToday);

        // =========================
        // CURRENT STREAKS LIST
        // =========================
        List<HabitStreakDTO> streakList = new ArrayList<>();

        for (Habit habit : habits) {

            StreakDTO streak =
                    habitLogService.getStreak(habit.getId());

            HabitStreakDTO s = new HabitStreakDTO();
            s.setHabitName(habit.getName());
            s.setCurrentStreak(streak.getCurrentStreak());
            s.setLongestStreak(streak.getLongestStreak());

            streakList.add(s);
        }

        dto.setCurrentStreaks(streakList);

        // =========================
        // CONSISTENCY SCORE
        // =========================
        int totalLogs = 0;
        int completedLogs = 0;

        for (Habit habit : habits) {

            List<HabitLog> logs =
                    habitLogRepository
                            .findByHabitIdOrderByLogDateAsc(
                                    habit.getId());

            totalLogs += logs.size();

            for (HabitLog log : logs) {
                if (log.isCompleted()) {
                    completedLogs++;
                }
            }
        }

        int score = 0;
        if (totalLogs > 0) {
            score = (completedLogs * 100) / totalLogs;
        }

        dto.setConsistencyScore(score);

        return dto;
    }
}