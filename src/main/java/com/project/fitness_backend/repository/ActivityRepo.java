package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity,Long> {
    List<Activity> findByUserUserIdOrderByActivityDateDesc(Long userId);
    List<Activity> findByUserUserIdAndActivityDateBetween(Long userId, LocalDate from,LocalDate to);
}
