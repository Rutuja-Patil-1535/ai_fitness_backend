package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkOutPlanRepo extends JpaRepository<WorkoutPlan,Long> {
    Optional<WorkoutPlan> findTopByUserUserIdOrderByGeneratedAtDesc(Long userId);
    long countByUserUserId(Long userId);
}
