package com.project.fitness_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate activityDate;

    @Column(nullable = false)
    private Integer steps;

    @Column(nullable = false)
    private Double caloriesBurned;

    @Column(nullable = false)
    private Integer workoutDurationMin;

    @Column( nullable = false)
    private Double waterIntakeLiters;

    @Column(nullable = false)
    private Double sleepHours;

    @Column( nullable = false)
    private LocalDateTime loggedAt;
}
