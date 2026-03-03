package com.litmus7.Streakify.controller;

import com.litmus7.Streakify.dto.DashboardDTO;
import com.litmus7.Streakify.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/users/{userId}/dashboard")
    public ResponseEntity<DashboardDTO> getDashboard(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getDashboard(userId));
    }
}