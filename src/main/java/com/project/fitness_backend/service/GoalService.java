package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.GoalRequest;
import com.project.fitness_backend.dto.GoalResponse;
import com.project.fitness_backend.exceptions.ResourceNotFoundException;
import com.project.fitness_backend.model.Goal;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.repository.GoalRepo;
import com.project.fitness_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalService {
    private final GoalRepo goalRepository;
    private final UserRepo userRepository;
    public GoalResponse saveGoal(GoalRequest request,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not Found with this Id: "+userId));
        Goal goal= Goal.builder()
                .goalType(request.getGoalType())
                .createdAt(LocalDateTime.now())
                .deadline(request.getDeadline())
                .targetValue(request.getTargetValue())
                .user(user)
                .build();
        goalRepository.save(goal);
        return mapToResponse(goal);
    }
    private GoalResponse mapToResponse(Goal goal) {
        return GoalResponse.builder()
                .id(goal.getId())
                .goalType(goal.getGoalType().name())
                .targetValue(goal.getTargetValue())
                .deadline(goal.getDeadline())
                .status(goal.getStatus().name())
                .createdAt(goal.getCreatedAt())
                .build();
    }
    public List<GoalResponse> findAllGoalsById(Long userId){
         return goalRepository.findByUserUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
