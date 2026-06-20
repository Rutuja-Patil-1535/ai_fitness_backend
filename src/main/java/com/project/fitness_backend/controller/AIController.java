package com.project.fitness_backend.controller;

import com.project.fitness_backend.dto.AIRecommendationResponse;
import com.project.fitness_backend.dto.WorkOutPlanResponse;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.service.AIService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;

    // Generate a new workout plan
    @PostMapping("/workout-plan")
    public ResponseEntity<WorkOutPlanResponse> generateWorkoutPlan(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aiService.generateWorkOutPlan(user.getUserId()));
    }

    // Get all stored recommendations
    @GetMapping("/recommendations")
    public ResponseEntity<List<AIRecommendationResponse>> getRecommendations(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(aiService.getRecommendations(user.getUserId()));
    }

    // Generate weekly progress analysis
    @PostMapping("/progress")
    public ResponseEntity<AIRecommendationResponse> generateProgressReport(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(aiService.generateProgressReport(user.getUserId()));
    }
}