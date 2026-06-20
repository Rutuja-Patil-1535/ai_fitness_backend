package com.project.fitness_backend.model;

import com.project.fitness_backend.enums.RecommendationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private RecommendationType recommendationType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column( nullable = false)
    private LocalDateTime generatedAt;
}
