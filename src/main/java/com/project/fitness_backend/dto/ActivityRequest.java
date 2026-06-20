package com.project.fitness_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ActivityRequest {

    @NotNull(message = "Activity date is required")
    @PastOrPresent(message = "date Should be in the present or past")
    private LocalDate activityDate;

    @NotNull(message = "Steps are required")
    @Min(value = 1, message = "Steps must be greater than 0")
    private Integer steps;

    @NotNull(message = "Calories burned is required")
    private Double caloriesBurned;

    @NotNull(message = "Workout duration is required")
    private Integer workoutDurationMin;

    @NotNull(message = "Water intake is required")
    private Double waterIntakeLiters;

    @NotNull(message = "Sleep hours are required")
    private Double sleepHours;
}
