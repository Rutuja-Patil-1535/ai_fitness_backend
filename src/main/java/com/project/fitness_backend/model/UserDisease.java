package com.project.fitness_backend.model;

import com.project.fitness_backend.enums.GoalStatus;
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
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "diseaseId"})
)
public class UserDisease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="userId")
    private User user;
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumn(name="diseaseId")
    private Disease disease;

}
