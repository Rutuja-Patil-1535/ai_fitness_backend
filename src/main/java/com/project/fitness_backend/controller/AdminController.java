package com.project.fitness_backend.controller;

import com.project.fitness_backend.dto.*;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.WorkExecutor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<UserSummaryResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(adminService.getUsers(page, size));
    }

    @PutMapping("/user/{id}/status")
    public ResponseEntity updateUserStatus(@PathVariable("id") Long userId, @RequestBody UserStatusRequest request) {
        return ResponseEntity.ok(adminService.updateUserStatus(userId, request));
    }

    @PostMapping("/addAdmin")
    public ResponseEntity<UserResponse> addAdmin(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.addAdmin(request));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashBoardResponse> dashBoard() {
        return ResponseEntity.ok(adminService.getDashBoard());
    }

    @PostMapping("/addDisease")
    public ResponseEntity<DiseaseResponse> addDisease(@Valid @RequestBody DiseaseRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.addDisease(request));
    }

    @GetMapping("/allDiseases")
    public List<DiseaseResponse> findAll() {
        return adminService.findAllDisease();
    }

    @PutMapping("/updateDisease/{id}")
    public ResponseEntity<DiseaseResponse> updateDisease(@PathVariable Long id, @Valid @RequestBody DiseaseRequest request) {
        return ResponseEntity.ok(adminService.updateDisease(id, request));
    }

    @DeleteMapping("/deleteDisease/{id}")
    public ResponseEntity<DiseaseResponse> deleteDisease(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteDisease(id));
    }

    @GetMapping("/aiMonitoring")
    public ResponseEntity<AiMonitoringResponse> getAiMonitoring() {
        return ResponseEntity.ok(adminService.aiMonitoring());
    }

    @PostMapping("/addWorkOutTemplate")
    public ResponseEntity<WorkOutTemplateResponse> addWorkOutTemplate(@Valid @RequestBody WorkOutTemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.addWorkOutTemplate(request));
    }

    @GetMapping("/getAllWorkOutTemplate")
    public List<WorkOutTemplateResponse> getAllWorkOutTemplate() {
        return adminService.getAllWorkOutTemplate();
    }

    @PutMapping("/updateWorkOutTemplate/{id}")
    public ResponseEntity<WorkOutTemplateResponse> updateWorkOutTemplate(@PathVariable Long id, @Valid @RequestBody WorkOutTemplateRequest request) {
        return ResponseEntity.ok(adminService.updateWorkOutTemplateResponse(id, request));
    }

    @DeleteMapping("/deleteWorkOutTemplate/{id}")
    public ResponseEntity<WorkOutTemplateResponse> delteeWorkOutTemplate(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteWorkOutTemplateResponse(id));
    }

}
