package com.project.fitness_backend.model;

import com.project.fitness_backend.enums.ActivityLevel;
import com.project.fitness_backend.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long healthId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @Column(nullable=false)
    private Integer age;
    @Column(nullable=false)
    private Double weightKg;
    @Column(nullable=false)
    private Double heightCm;
    @Column(nullable=false)
    private Double bmi;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ActivityLevel activityLevel;
    @Column(nullable=false)
    private LocalDateTime recordedAt;

}
