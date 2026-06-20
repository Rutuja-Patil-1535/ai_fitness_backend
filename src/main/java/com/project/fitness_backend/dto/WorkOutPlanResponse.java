package com.project.fitness_backend.dto;

import com.project.fitness_backend.model.Goal;
import com.project.fitness_backend.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkOutPlanResponse {
    private Long id;
    private String planContent;
    private String goalType;
    private LocalDateTime generatedAt;
}
