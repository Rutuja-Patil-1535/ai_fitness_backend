package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.HealthInfoRequest;
import com.project.fitness_backend.dto.HealthInfoResponse;
import com.project.fitness_backend.model.HealthInfo;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.repository.HealthInfoRep;
import com.project.fitness_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HealthService {
    private final HealthInfoRep healthInfoRepository;
    private final UserRepo userRepository;
    public Double calculateBmi(Double heightCm,Double weightKg){
       double bmi=  weightKg/Math.pow(heightCm/100,2);
       return Math.round(bmi*100.0)/100.0;
    }
    public HealthInfoResponse saveHealthInfo(HealthInfoRequest request,Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("User Not found By This id: "+userId));
        HealthInfo healthInfo=HealthInfo.builder()
                .user(user)
                .bmi(calculateBmi(request.getHeightCm(), request.getWeightKg()))
                .recordedAt(LocalDateTime.now())
                .age(request.getAge())
                .activityLevel(request.getActivityLevel())
                .gender(request.getGender())
                .weightKg(request.getWeightKg())
                .heightCm(request.getHeightCm())
                .build();
        healthInfoRepository.save(healthInfo);
        HealthInfoResponse response= HealthInfoResponse.builder()
                .healthId(healthInfo.getHealthId())
                .activityLevel(healthInfo.getActivityLevel())
                .age(healthInfo.getAge())
                .bmi(healthInfo.getBmi())
                .gender(healthInfo.getGender())
                .heightCm(healthInfo.getHeightCm())
                .recordedAt(healthInfo.getRecordedAt())
                .weightKg(healthInfo.getWeightKg())
                .build();
        return response;
    }
    public HealthInfoResponse findById(Long userId){
        //findByUserUserId
        HealthInfo healthInfo=healthInfoRepository.findTopByUserUserIdOrderByRecordedAtDesc(userId).orElseThrow(()->new RuntimeException("User Not found with this id:"+userId));
        HealthInfoResponse response= HealthInfoResponse.builder()
                .healthId(healthInfo.getHealthId())
                .activityLevel(healthInfo.getActivityLevel())
                .age(healthInfo.getAge())
                .bmi(healthInfo.getBmi())
                .gender(healthInfo.getGender())
                .heightCm(healthInfo.getHeightCm())
                .recordedAt(healthInfo.getRecordedAt())
                .weightKg(healthInfo.getWeightKg())
                .build();
        return response;

    }
}
