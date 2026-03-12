package com.litmus7.Streakify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "habit_logs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"habit_id", "log_date"})
        }

)
@Getter
@Setter
public class HabitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "habit_id", nullable = false)
    @JsonIgnore
    private Habit habit;
}