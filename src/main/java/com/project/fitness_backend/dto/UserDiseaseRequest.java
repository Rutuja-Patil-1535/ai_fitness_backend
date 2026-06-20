package com.project.fitness_backend.dto;

import com.project.fitness_backend.model.Disease;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDiseaseRequest {
    @NotNull(message="Please Enter disease id")
    private Long diseaseId;
}
