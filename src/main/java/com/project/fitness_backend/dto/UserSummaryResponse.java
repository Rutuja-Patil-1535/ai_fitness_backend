package com.project.fitness_backend.dto;

import com.project.fitness_backend.enums.Role;
import lombok.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryResponse {
    private Long userId;
    private String name;
    private String email;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;
}
