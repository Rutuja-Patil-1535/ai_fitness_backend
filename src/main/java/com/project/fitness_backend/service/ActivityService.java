package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.ActivityRequest;
import com.project.fitness_backend.dto.ActivityResponse;
import com.project.fitness_backend.exceptions.ResourceNotFoundException;
import com.project.fitness_backend.model.Activity;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.repository.ActivityRepo;
import com.project.fitness_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepo activityRepository;
    private final UserRepo userRepository;
    public ActivityResponse createActivity(ActivityRequest request,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found with this id:"+userId));
        Activity activity= Activity.builder()
                .activityDate(request.getActivityDate())
                .steps(request.getSteps())
                .caloriesBurned(request.getCaloriesBurned())
                .user(user)
                .sleepHours(request.getSleepHours())
                .waterIntakeLiters(request.getWaterIntakeLiters())
                .workoutDurationMin(request.getWorkoutDurationMin())
                .loggedAt(LocalDateTime.now())
                .build();
        activityRepository.save(activity);
        return mapToResponse(activity);
    }
    private ActivityResponse mapToResponse(Activity activity){
        ActivityResponse response= ActivityResponse.builder()
                .id(activity.getId())
                .activityDate(activity.getActivityDate())
                .userId(activity.getUser().getUserId())
                .caloriesBurned(activity.getCaloriesBurned())
                .steps(activity.getSteps())
                .workoutDurationMin(activity.getWorkoutDurationMin())
                .waterIntakeLiters(activity.getWaterIntakeLiters())
                .loggedAt(activity.getLoggedAt())
                .sleepHours(activity.getSleepHours())
                .build();
        return response;
    }
    public List<ActivityResponse> findActivitesById(Long userId){
        return activityRepository.findByUserUserIdOrderByActivityDateDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
