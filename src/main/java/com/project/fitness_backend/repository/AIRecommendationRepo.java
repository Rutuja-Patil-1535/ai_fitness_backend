package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.AIRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AIRecommendationRepo extends JpaRepository<AIRecommendation,Long> {
    List<AIRecommendation> findByUserUserIdOrderByGeneratedAtDesc(Long userId);
    @Query("SELECT r.user.userId, COUNT(r) FROM AIRecommendation r GROUP BY r.user.userId")
    List<Object[]> countGroupedByUser();

}
