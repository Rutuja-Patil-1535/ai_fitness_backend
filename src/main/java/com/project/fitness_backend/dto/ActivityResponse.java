package com.project.fitness_backend.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private Long id;
    private Long userId;
    private LocalDate activityDate;
    private Integer steps;
    private Double caloriesBurned;
    private Integer workoutDurationMin;
    private Double waterIntakeLiters;
    private Double sleepHours;
    private LocalDateTime loggedAt;
}
