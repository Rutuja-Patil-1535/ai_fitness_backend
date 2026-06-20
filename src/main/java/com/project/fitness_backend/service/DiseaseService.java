package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.DiseaseRequest;
import com.project.fitness_backend.dto.DiseaseResponse;
import com.project.fitness_backend.dto.UserDiseaseRequest;
import com.project.fitness_backend.exceptions.ResourceNotFoundException;
import com.project.fitness_backend.model.Disease;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.model.UserDisease;
import com.project.fitness_backend.repository.DiseaseRepo;
import com.project.fitness_backend.repository.UserDiseaseRepo;
import com.project.fitness_backend.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepo diseaseRepository;
    private final UserDiseaseRepo userDiseaseRepo;
    private final UserRepo userRepository;
    public List<DiseaseResponse> getAllDiseases() {
        return diseaseRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void associateDisease(UserDiseaseRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Disease disease = diseaseRepository.findById(request.getDiseaseId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Disease not found with id: " + request.getDiseaseId()));

        // skip if already associated
        if (userDiseaseRepo.existsByUserUserIdAndDiseaseId(userId, request.getDiseaseId())) {
            return;
        }

        UserDisease userDisease = UserDisease.builder()
                .user(user)
                .disease(disease)
                .build();

        userDiseaseRepo.save(userDisease);
    }

    public List<DiseaseResponse> getUserDiseases(Long userId) {
        return userDiseaseRepo.findByUserUserId(userId)
                .stream()
                .map(ud -> mapToResponse(ud.getDisease()))
                .collect(Collectors.toList());
    }

    private DiseaseResponse mapToResponse(Disease disease) {
        return DiseaseResponse.builder()
                .id(disease.getId())
                .name(disease.getName())
                .description(disease.getDescription())
                .build();
    }
}
