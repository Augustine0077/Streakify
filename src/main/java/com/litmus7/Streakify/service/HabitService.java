package com.litmus7.Streakify.service;

import com.litmus7.Streakify.dto.HabitDTO;
import com.litmus7.Streakify.dto.HabitRequestDTO;
import com.litmus7.Streakify.entity.Habit;
import com.litmus7.Streakify.entity.User;
import com.litmus7.Streakify.exception.ResourceNotFoundException;
import com.litmus7.Streakify.repository.HabitRepository;
import com.litmus7.Streakify.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitService(HabitRepository habitRepository,
                        UserRepository userRepository) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    // ==============================
    // CREATE HABIT
    // ==============================
    public HabitDTO createHabit(HabitRequestDTO request) {

        // CHECK USER EXISTS
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // VALIDATE TARGET DAYS (1 - 7)
        if (request.getTargetDaysPerWeek() < 1 ||
                request.getTargetDaysPerWeek() > 7) {

            throw new RuntimeException(
                    "Target days per week must be between 1 and 7");
        }

        // DUPLICATE HABIT CHECK
        if (habitRepository.existsByUserIdAndName(
                request.getUserId(),
                request.getName())) {

            throw new RuntimeException(
                    "Cannot add duplicate habit name for this user");
        }

        Habit habit = new Habit();
        habit.setName(request.getName());
        habit.setTargetDaysPerWeek(request.getTargetDaysPerWeek());
        habit.setUser(user);

        return mapToDTO(habitRepository.save(habit));
    }

    // ==============================
    // GET USER HABITS
    // ==============================
    public List<HabitDTO> getHabits(Long userId) {

        return habitRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ==============================
    // DELETE HABIT
    // ==============================
    public void deleteHabit(Long id) {

        Habit habit = habitRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Habit not found"));

        habitRepository.delete(habit);
    }

    // ==============================
    // MAP ENTITY → DTO
    // ==============================
    private HabitDTO mapToDTO(Habit habit) {

        HabitDTO dto = new HabitDTO();
        dto.setId(habit.getId());
        dto.setName(habit.getName());
        dto.setTargetDaysPerWeek(habit.getTargetDaysPerWeek());

        return dto;
    }
}