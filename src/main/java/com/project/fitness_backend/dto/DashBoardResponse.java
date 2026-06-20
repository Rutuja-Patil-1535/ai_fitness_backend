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
public class DashBoardResponse {
    public long totalUsers;
    public long activeUsers;
    public long totalWorkoutsCompleted;
    public Map<String,Long> popularGoalTypes;


}
