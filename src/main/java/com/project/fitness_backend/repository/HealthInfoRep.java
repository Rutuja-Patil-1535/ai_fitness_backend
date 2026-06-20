package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.HealthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthInfoRep extends JpaRepository<HealthInfo,Long> {
    Optional<HealthInfo> findTopByUserUserIdOrderByRecordedAtDesc(Long userId);
}
