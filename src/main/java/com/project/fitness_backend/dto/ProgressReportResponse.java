package com.project.fitness_backend.dto;

import com.project.fitness_backend.enums.RecommendationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgressReportResponse {
    public RecommendationType recommendationType;
    public String content;
    public LocalDateTime generatedAt;
}
