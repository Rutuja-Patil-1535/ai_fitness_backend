package com.project.fitness_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AiMonitoringResponse {
    private long totalApiRequests;
    private long totalRecommendations;
    private Map<String, Long> recommendationsPerUser;

}
