package com.project.fitness_backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DiseaseRequest {
    @NotBlank(message="Please enter disease name")
    private String name;
    @NotBlank(message="Please enter disease description")
    private String description;
}
