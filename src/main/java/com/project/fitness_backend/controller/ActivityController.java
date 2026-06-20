package com.project.fitness_backend.controller;

import com.project.fitness_backend.dto.ActivityRequest;
import com.project.fitness_backend.dto.ActivityResponse;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.service.ActivityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;
    @PostMapping()
    public ResponseEntity<ActivityResponse> createActivity(@AuthenticationPrincipal User user, @Valid @RequestBody ActivityRequest request){
       return ResponseEntity.status(HttpStatus.CREATED).body(activityService.createActivity(request,user.getUserId()));

    }
    @GetMapping()
    public ResponseEntity<List<ActivityResponse>> getAllActivites(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(activityService.findActivitesById(user.getUserId()));
    }
}
