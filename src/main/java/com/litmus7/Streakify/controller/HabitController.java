package com.litmus7.Streakify.controller;

import com.litmus7.Streakify.dto.HabitDTO;
import com.litmus7.Streakify.dto.HabitRequestDTO;
import com.litmus7.Streakify.service.HabitService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // =====================================
    // CREATE HABIT
    // =====================================
    @PostMapping("/habits")
    public ResponseEntity<HabitDTO> createHabit(
            @Valid @RequestBody HabitRequestDTO request) {

        return ResponseEntity.ok(
                habitService.createHabit(request));
    }

    // =====================================
    // GET USER HABITS
    // =====================================
    @GetMapping("/users/{userId}/habits")
    public ResponseEntity<List<HabitDTO>> getHabits(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                habitService.getHabits(userId));
    }

    // =====================================
    // DELETE HABIT
    // =====================================
    @DeleteMapping("/habits/{id}")
    public ResponseEntity<String> deleteHabit(
            @PathVariable Long id) {

        habitService.deleteHabit(id);
        return ResponseEntity.ok("Habit deleted");
    }
}