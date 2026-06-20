package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.Disease;
import com.project.fitness_backend.model.UserDisease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDiseaseRepo extends JpaRepository<UserDisease,Long> {
    List<UserDisease> findByUserUserId(Long userId);
    boolean existsByUserUserIdAndDiseaseId(Long userId, Long diseaseId);
}
