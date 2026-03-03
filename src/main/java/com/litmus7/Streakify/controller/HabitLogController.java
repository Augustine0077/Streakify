package com.litmus7.Streakify.controller;

import com.litmus7.Streakify.dto.HabitLogDTO;
import com.litmus7.Streakify.dto.HabitLogRequestDTO;
import com.litmus7.Streakify.dto.StreakDTO;
import com.litmus7.Streakify.service.HabitLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class HabitLogController {

    private final HabitLogService habitLogService;

    public HabitLogController(HabitLogService habitLogService) {
        this.habitLogService = habitLogService;
    }

    // ==================================================
    // CREATE LOG
    // POST /habits/{habitId}/logs
    // ==================================================
    @PostMapping("/habits/{habitId}/logs")
    public ResponseEntity<HabitLogDTO> createLog(
            @PathVariable Long habitId,
            @RequestBody HabitLogRequestDTO request) {

        return ResponseEntity.ok(
                habitLogService.createLog(habitId, request));
    }

    // ==================================================
    // ⭐ NEW — UPDATE LOG
    // PUT /habits/{habitId}/logs/{date}
    // ==================================================
    @PutMapping("/habits/{habitId}/logs/{date}")
    public ResponseEntity<HabitLogDTO> updateLog(
            @PathVariable Long habitId,
            @PathVariable LocalDate date,
            @RequestBody HabitLogRequestDTO request) {

        return ResponseEntity.ok(
                habitLogService.updateLog(habitId, date, request));
    }

    // ==================================================
    // ⭐ NEW — GET ALL LOGS
    // GET /habits/{habitId}/logs
    // ==================================================
    @GetMapping("/habits/{habitId}/logs")
    public ResponseEntity<List<HabitLogDTO>> getLogs(
            @PathVariable Long habitId) {

        return ResponseEntity.ok(
                habitLogService.getLogs(habitId));
    }

    // ==================================================
    // GET STREAK
    // GET /habits/{habitId}/streak
    // ==================================================
    @GetMapping("/habits/{habitId}/streak")
    public ResponseEntity<StreakDTO> getStreak(
            @PathVariable Long habitId) {

        return ResponseEntity.ok(
                habitLogService.getStreak(habitId));
    }
}