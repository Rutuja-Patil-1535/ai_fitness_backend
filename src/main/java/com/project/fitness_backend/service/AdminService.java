package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.*;
import com.project.fitness_backend.enums.Role;
import com.project.fitness_backend.exceptions.DuplicateDiseaseException;
import com.project.fitness_backend.exceptions.ResourceNotFoundException;
import com.project.fitness_backend.exceptions.UnprocessableEntityException;
import com.project.fitness_backend.model.Disease;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.model.WorkoutTemplate;
import com.project.fitness_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final WorkOutPlanRepo workOutPlanRepository;
    private final GoalRepo goalRepository;
    private final DiseaseRepo diseaseRepository;
    private final AIRecommendationRepo aiRecommendationRepository;
    private final WorkOutTemplateRepo workOutTemplateRepository;
    public Page<UserSummaryResponse> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(user -> UserSummaryResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build());
    }

    public String updateUserStatus(Long userId, UserStatusRequest request){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with not found with user id:"+userId));
        user.setActive(request.active);
        userRepository.save(user);
        return "User status updated successfully";
    }
    public UserResponse addAdmin(RegisterRequest request){
        User user=User.builder()
                        .role(Role.valueOf("ADMIN"))
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .email(request.getEmail())
                .build();
        userRepository.save(user);
        return UserResponse.builder()
                .id(user.getUserId())
                .active(user.isActive())
                .role(String.valueOf(user.getRole()))
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
    /*
    public long totalUsers;
    public long activeUsers;
    public long totalWorkoutsCompleted;
    public Map<String,Long> popularGoalTypes;
    */
    public DashBoardResponse getDashBoard(){
        DashBoardResponse response=new DashBoardResponse();
        response.setTotalUsers(userRepository.count());
        response.setActiveUsers(userRepository.countByActive(true));
        response.setTotalWorkoutsCompleted(workOutPlanRepository.count());
        response.setPopularGoalTypes(goalRepository.countGroupedByGoalType().stream()
                .collect(Collectors.toMap(
                        row -> row[0].toString(),  // GoalType name
                        row -> (Long) row[1]        // count
                )));
        return response;
    }
    private DiseaseResponse mapToDiseaseResponse(Disease disease){
        return new DiseaseResponse(disease.getId(), disease.getName(), disease.getDescription());
    }
    public DiseaseResponse addDisease(DiseaseRequest request){
        if(diseaseRepository.existsByName(request.getName())){
            throw new DuplicateDiseaseException("Disease already exists with the disease name:"+request.getName());
        }
        Disease disease= Disease.builder()
                .description(request.getDescription())
                .name(request.getName())
                .build();
        diseaseRepository.save(disease);
        return mapToDiseaseResponse(disease);

    }
    public DiseaseResponse getDiseaseByName(String name){
        Disease disease=diseaseRepository.findByName(name).orElseThrow(()->
                new ResourceNotFoundException("Disease not found with the name:"+name));
        return mapToDiseaseResponse(disease);
    }
    public List<DiseaseResponse> findAllDisease(){
        return diseaseRepository.findAll().stream().map(this::mapToDiseaseResponse).collect(Collectors.toList());
    }
    public DiseaseResponse updateDisease(Long id,DiseaseRequest request){
        Disease disease=diseaseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Disease not found with this id:"+id));
        disease.setName(request.getName());
        disease.setDescription(request.getDescription());
        diseaseRepository.save(disease);
        return mapToDiseaseResponse(disease);
    }
    public DiseaseResponse deleteDisease(Long id){
        Disease disease=diseaseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Disease not found with this id:"+id));
        diseaseRepository.deleteById(id);
        return mapToDiseaseResponse(disease);
    }
    /*
    private long totalApiRequests;
    private long totalRecommendations;
    private Map<String, Long> recommendationsPerUser;
     */
    public AiMonitoringResponse aiMonitoring(){
        long total=aiRecommendationRepository.count();
        return AiMonitoringResponse.builder()
                .recommendationsPerUser(aiRecommendationRepository.countGroupedByUser().stream()
                        .collect(Collectors.toMap(
                                row -> row[0].toString(),  // user id
                                row -> (Long) row[1]        // count
                        )))
                .totalRecommendations(total)
                .totalApiRequests(total)
                .build();
    }
    private WorkOutTemplateResponse mapToWorkOutTemplateResponse(WorkoutTemplate template){
        return WorkOutTemplateResponse.builder()
                .createdAt(template.getCreatedAt())
                .id(template.getId())
                .content(template.getContent())
                .name(template.getName())
                .build();
    }
    public WorkOutTemplateResponse addWorkOutTemplate(WorkOutTemplateRequest request){
        WorkoutTemplate workoutTemplate=WorkoutTemplate.builder()
                .content(request.content)
                .name(request.name)
                .createdAt(LocalDateTime.now())
                .build();
        WorkoutTemplate saved=workOutTemplateRepository.save(workoutTemplate);
        return mapToWorkOutTemplateResponse(saved);
    }
    public List<WorkOutTemplateResponse> getAllWorkOutTemplate(){
        return workOutTemplateRepository.findAll().stream().map(this::mapToWorkOutTemplateResponse).collect(Collectors.toList());
    }
    public WorkOutTemplateResponse updateWorkOutTemplateResponse(Long id,WorkOutTemplateRequest request){
        WorkoutTemplate workoutTemplate=workOutTemplateRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Workout template not found with is:"+id));
        workoutTemplate.setName(request.name);
        workoutTemplate.setContent(request.content);
        WorkoutTemplate saved=workOutTemplateRepository.save(workoutTemplate);
        return mapToWorkOutTemplateResponse(saved);
    }
    public WorkOutTemplateResponse deleteWorkOutTemplateResponse(Long id){
        WorkoutTemplate workoutTemplate=workOutTemplateRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Workout template not found with is:"+id));
       workOutTemplateRepository.deleteById(id);
        return mapToWorkOutTemplateResponse(workoutTemplate);
    }

}
