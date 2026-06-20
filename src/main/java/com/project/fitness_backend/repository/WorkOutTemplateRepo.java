package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.WorkoutTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOutTemplateRepo extends JpaRepository<WorkoutTemplate,Long> {
}
