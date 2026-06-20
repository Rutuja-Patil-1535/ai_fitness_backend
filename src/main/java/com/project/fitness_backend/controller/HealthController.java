package com.project.fitness_backend.controller;

import com.project.fitness_backend.dto.HealthInfoRequest;
import com.project.fitness_backend.dto.HealthInfoResponse;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.service.HealthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/healthInfo")
public class HealthController {
    @Autowired
    private HealthService healthService;
    @PostMapping()
    public ResponseEntity<HealthInfoResponse> addHealthInfo(@AuthenticationPrincipal User user,
            @Valid @RequestBody HealthInfoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(healthService.saveHealthInfo(request, user.getUserId()));
    }
    @GetMapping()
    public ResponseEntity<HealthInfoResponse> getHealthInfo(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(healthService.findById(user.getUserId()));
    }
}
