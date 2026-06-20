package com.project.fitness_backend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkOutTemplateResponse {
    public Long id;
    public String name;
    public String content;
    public LocalDateTime createdAt;
}
