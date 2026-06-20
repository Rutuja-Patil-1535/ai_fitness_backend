package com.project.fitness_backend.repository;

import com.project.fitness_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    long countByActive(boolean active);
}
