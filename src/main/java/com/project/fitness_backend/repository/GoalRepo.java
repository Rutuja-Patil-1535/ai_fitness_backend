package com.project.fitness_backend.repository;

import com.project.fitness_backend.enums.GoalStatus;
import com.project.fitness_backend.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoalRepo extends JpaRepository<Goal,Long> {
    List<Goal> findByUserUserId(Long userId);
    Optional<Goal> findTopByUserUserIdAndStatus(Long userId, GoalStatus status);
    @Query("SELECT g.goalType, COUNT(g) FROM Goal g GROUP BY g.goalType")
    List<Object[]> countGroupedByGoalType();
    //here object is a g.goalType and count(g)

}
