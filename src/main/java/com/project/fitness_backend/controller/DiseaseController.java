package com.project.fitness_backend.controller;

import com.project.fitness_backend.dto.DiseaseRequest;
import com.project.fitness_backend.dto.DiseaseResponse;
import com.project.fitness_backend.dto.UserDiseaseRequest;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.service.DiseaseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@Controller
@RequiredArgsConstructor
@RequestMapping("/disease/users")
public class DiseaseController {
    private final DiseaseService diseaseService;
    @PostMapping
    public ResponseEntity<Void> associateDisease(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserDiseaseRequest request) {
        diseaseService.associateDisease(request, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<DiseaseResponse>> getUserDiseases(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(diseaseService.getUserDiseases(user.getUserId()));
    }
    @GetMapping("/all")
public ResponseEntity<List<DiseaseResponse>> getAllDiseases() {
    return ResponseEntity.ok(diseaseService.getAllDiseases());
}

}
