package com.project.fitness_backend.dto;

import com.project.fitness_backend.enums.RecommendationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIRecommendationResponse {
    public Long id;
    public RecommendationType recommendationType;
    public String content;
    public LocalDateTime generatedAt;
}
