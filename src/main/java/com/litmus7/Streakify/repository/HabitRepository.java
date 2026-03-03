package com.litmus7.Streakify.repository;

import com.litmus7.Streakify.entity.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByUserId(Long userId);

    // ⭐ ADDED (duplicate check)
    boolean existsByUserIdAndName(Long userId, String name);
}