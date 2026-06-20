package com.project.fitness_backend.dto;

import com.project.fitness_backend.enums.ActivityLevel;
import com.project.fitness_backend.enums.Gender;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthInfoResponse {
    private Long healthId;
    private Integer age;
    private Double weightKg;
    private Double heightCm;
    private Double bmi;
    private Gender gender;
    private ActivityLevel activityLevel;
    private LocalDateTime recordedAt;
}
