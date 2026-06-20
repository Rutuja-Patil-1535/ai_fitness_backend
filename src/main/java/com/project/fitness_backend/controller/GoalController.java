package com.project.fitness_backend.controller;

import com.project.fitness_backend.dto.GoalRequest;
import com.project.fitness_backend.dto.GoalResponse;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.service.GoalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/goals")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;
    @PostMapping()
    public ResponseEntity<GoalResponse> createGoal(@AuthenticationPrincipal User user,@Valid @RequestBody GoalRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(goalService.saveGoal(request, user.getUserId()));
    }
    @GetMapping()
    public ResponseEntity<List<GoalResponse>> getAllGoals(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(goalService.findAllGoalsById(user.getUserId()));
    }

}
