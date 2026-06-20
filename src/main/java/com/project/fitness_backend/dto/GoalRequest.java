package com.project.fitness_backend.dto;

import com.project.fitness_backend.enums.GoalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class GoalRequest {

    @NotNull(message = "Goal type is required")
    private GoalType goalType;

    @NotNull(message = "Target value is required")
    private Double targetValue;

    @NotNull(message = "Deadline is required")
    @Future(message = "Deadline must be in the future")
    private LocalDate deadline;
}
