package com.project.fitness_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoalResponse {
    private Long id;
    private String goalType;
    private Double targetValue;
    private LocalDate deadline;
    private String status ;
    private LocalDateTime createdAt;
}
