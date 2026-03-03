package com.litmus7.Streakify.service;

import com.litmus7.Streakify.dto.HabitLogDTO;
import com.litmus7.Streakify.dto.HabitLogRequestDTO;
import com.litmus7.Streakify.dto.StreakDTO;
import com.litmus7.Streakify.entity.Habit;
import com.litmus7.Streakify.entity.HabitLog;
import com.litmus7.Streakify.exception.ResourceNotFoundException;
import com.litmus7.Streakify.repository.HabitLogRepository;
import com.litmus7.Streakify.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitLogService {

    private final HabitRepository habitRepository;
    private final HabitLogRepository habitLogRepository;

    public HabitLogService(HabitRepository habitRepository,
                           HabitLogRepository habitLogRepository) {
        this.habitRepository = habitRepository;
        this.habitLogRepository = habitLogRepository;
    }

    // ==================================================
    // CREATE LOG
    // ==================================================
    public HabitLogDTO createLog(Long habitId,
                                 HabitLogRequestDTO request) {

        Habit habit = habitRepository.findById(habitId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Habit not found"));

        // NULL CHECK
        if (request.getLogDate() == null) {
            throw new RuntimeException("Log date is required");
        }

        // FUTURE DATE CHECK
        if (request.getLogDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Cannot log future date");
        }

        // ⭐ NEW: BEFORE HABIT CREATION CHECK
        LocalDate habitCreatedDate =
                habit.getCreatedAt().toLocalDate();

        if (request.getLogDate().isBefore(habitCreatedDate)) {
            throw new RuntimeException(
                    "Cannot log before habit creation date");
        }

        // DUPLICATE CHECK
        if (habitLogRepository
                .findByHabitIdAndLogDate(habitId,
                        request.getLogDate())
                .isPresent()) {

            throw new RuntimeException("Log already exists");
        }

        HabitLog log = new HabitLog();
        log.setHabit(habit);
        log.setLogDate(request.getLogDate());
        log.setCompleted(request.isCompleted());

        return mapToDTO(habitLogRepository.save(log));
    }

    // ==================================================
    // UPDATE LOG (EDIT)
    // ==================================================
    public HabitLogDTO updateLog(Long habitId,
                                 LocalDate date,
                                 HabitLogRequestDTO request) {

        HabitLog log = habitLogRepository
                .findByHabitIdAndLogDate(habitId, date)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Log not found"));

        log.setCompleted(request.isCompleted());

        return mapToDTO(habitLogRepository.save(log));
    }

    // ==================================================
    // GET ALL LOGS
    // ==================================================
    public List<HabitLogDTO> getLogs(Long habitId) {

        return habitLogRepository
                .findByHabitIdOrderByLogDateAsc(habitId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ==================================================
    // STREAK CALCULATION
    // ==================================================
    public StreakDTO getStreak(Long habitId) {

        List<HabitLog> logs =
                habitLogRepository
                        .findByHabitIdOrderByLogDateAsc(habitId);

        int current = 0;
        int longest = 0;
        int temp = 0;

        LocalDate previousDate = null;

        for (HabitLog log : logs) {

            if (!log.isCompleted()) {
                temp = 0;
                previousDate = log.getLogDate();
                continue;
            }

            if (previousDate != null &&
                    log.getLogDate()
                            .equals(previousDate.plusDays(1))) {

                temp++;
            } else {
                temp = 1;
            }

            longest = Math.max(longest, temp);
            previousDate = log.getLogDate();
        }

        current = temp;

        StreakDTO dto = new StreakDTO();
        dto.setCurrentStreak(current);
        dto.setLongestStreak(longest);

        return dto;
    }

    // ==================================================
    // ENTITY → DTO
    // ==================================================
    private HabitLogDTO mapToDTO(HabitLog log) {

        HabitLogDTO dto = new HabitLogDTO();
        dto.setId(log.getId());
        dto.setLogDate(log.getLogDate());
        dto.setCompleted(log.isCompleted());

        return dto;
    }
}