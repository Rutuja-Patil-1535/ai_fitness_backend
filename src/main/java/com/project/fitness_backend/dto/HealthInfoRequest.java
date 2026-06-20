package com.project.fitness_backend.dto;

import com.project.fitness_backend.enums.ActivityLevel;
import com.project.fitness_backend.enums.Gender;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HealthInfoRequest {

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @NotNull(message = "Weight is required")
    private Double weightKg;

    @NotNull(message = "Height is required")
    private Double heightCm;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Activity level is required")
    private ActivityLevel activityLevel;
}
