package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiseaseRepo extends JpaRepository<Disease,Long> {
    boolean existsByName(String name);
    Optional<Disease> findByName(String name);
    boolean existsById(Long id);
}
