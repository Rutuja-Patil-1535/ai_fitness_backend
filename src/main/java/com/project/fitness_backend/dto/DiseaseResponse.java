package com.project.fitness_backend.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiseaseResponse {
    private Long id;
    private String name;
    private String description;
}
