package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.AIRecommendationResponse;
import com.project.fitness_backend.dto.WorkOutPlanResponse;
import com.project.fitness_backend.enums.GoalStatus;
import com.project.fitness_backend.enums.RecommendationType;
import com.project.fitness_backend.exceptions.OpenAiApiException;
import com.project.fitness_backend.exceptions.ResourceNotFoundException;
import com.project.fitness_backend.exceptions.UnprocessableEntityException;
import com.project.fitness_backend.model.*;
import com.project.fitness_backend.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.*;

@Service
@RequiredArgsConstructor
public class AIService {
    private final HealthInfoRep healthInfoRepository;
    private final GoalRepo goalRepository;
    private final UserDiseaseRepo userDiseaseRepository;
    private final WorkOutPlanRepo workOutPlanRepository;
    private final ActivityRepo activityRepo;
    private final UserRepo userRepository;
    private final AIRecommendationRepo aiRecommendationRepository;

    @Value("${gemini.api.key}")
    private String geminiAiApiKey;
    @Value("${gemini.api.url}")
    private String geminiAiUrl;
    @Value("${gemini.api.model}")
    private String geminiAiModel;
    //generate work out plan
    @Transactional
    public WorkOutPlanResponse generateWorkOutPlan(Long userId){
        //check health info exist
        HealthInfo healthInfo=healthInfoRepository.findTopByUserUserIdOrderByRecordedAtDesc(userId).orElseThrow(()->new UnprocessableEntityException("Please submit your health information before generating workout plan"));
        //check active goal exist
        Goal activeGoal=goalRepository.findTopByUserUserIdAndStatus(userId, GoalStatus.ACTIVE).orElseThrow(()->new UnprocessableEntityException("Please set your active fitness goal before generating workout plan"));
        //get user diseases(optional can be empty)
        List<UserDisease> diseases=userDiseaseRepository.findByUserUserId(userId);
        //build prompt to send to yhe gemini api
        String prompt=buildWorkoutPrompt(healthInfo,activeGoal,diseases);
        //call gemini api and get the response text
        String planContent=callGeminiApi(prompt);
        //save workOutPlan and AI recommendation
        //transactional ensures that both are saved or neither is saved
        WorkoutPlan workoutPlan= WorkoutPlan.builder()
                .user(healthInfo.getUser())
                .generatedAt(LocalDateTime.now())
                .goal(activeGoal)
                .planContent(planContent)
                .build();
        workOutPlanRepository.save(workoutPlan);
        AIRecommendation aiRecommendation= AIRecommendation.builder()
                .user(healthInfo.getUser())
                .generatedAt(LocalDateTime.now())
                .recommendationType(RecommendationType.WORKOUT_PLAN)
                .content(planContent)
                .build();
        aiRecommendationRepository.save(aiRecommendation);
        return WorkOutPlanResponse.builder()
                .generatedAt(workoutPlan.getGeneratedAt())
                .goalType(workoutPlan.getPlanContent())
                .id(workoutPlan.getId())
                .planContent(workoutPlan.getPlanContent())
                .build();

    }
    //weekly progress
    @Transactional
    public AIRecommendationResponse generateProgressReport(Long userId){
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not found"));
        HealthInfo healthInfo=healthInfoRepository.findTopByUserUserIdOrderByRecordedAtDesc(userId).orElseThrow(()->new UnprocessableEntityException("Please Submit your health information"));
        Goal activeGoal=goalRepository.findTopByUserUserIdAndStatus(userId,GoalStatus.ACTIVE).orElseThrow(()->new UnprocessableEntityException("No active Goals found"));
        //get last 7 days of activities
        LocalDate from=LocalDate.now().minusDays(7);
        List<Activity> activities=activityRepo.findByUserUserIdAndActivityDateBetween(userId,from,LocalDate.now());
        //get latest workout plan(can be null if non generated yet)
        WorkoutPlan latestPlan=workOutPlanRepository.findTopByUserUserIdOrderByGeneratedAtDesc(userId).orElse(null);
        //build prompt and call gemini api
        String prompt=buildProgressPrompt(healthInfo,activeGoal,activities,latestPlan);
        String reportContent=callGeminiApi(prompt);
        AIRecommendation aiRecommendation= AIRecommendation.builder()
                .user(user)
                .recommendationType(RecommendationType.PROGRESS_REPORT)
                .content(reportContent)
                .generatedAt(LocalDateTime.now())
                .build();
        aiRecommendationRepository.save(aiRecommendation);
        return mapToResponse(aiRecommendation);
    }
    //get all recommendations
    public List<AIRecommendationResponse> getRecommendations(Long userId){
        return aiRecommendationRepository.findByUserUserIdOrderByGeneratedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    //gemini api call-this is the core integration
    private String callGeminiApi(String prompt){
        try{
            RestTemplate restTemplate=new RestTemplate();
            //authorization with your api key
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Gemini request body structure:
            // { "contents": [ { "parts": [ { "text": "your prompt" } ] } ] }
            //here send the request according to the gemini request body
            Map<String,Object> part=Map.of("text",prompt);
            Map<String,Object> content=Map.of("parts",List.of(part));
            Map<String,Object> requestBody=Map.of("contents",List.of(content));
            //here entity binds the request body and headers which are in the format of map
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // API key is appended as query parameter in the URL
            String urlWithKey = geminiAiUrl + "?key=" + geminiAiApiKey;
            //here map.class represent actual response be in the map
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    urlWithKey, entity, Map.class);

            // Gemini response structure:
            // { candidates: [ { content: { parts: [ { text: "..." } ] } } ] }
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, Object>> candidates =
                        (List<Map<String, Object>>) response.getBody().get("candidates");
                Map<String, Object> responseContent =
                        (Map<String, Object>) candidates.get(0).get("content");
                List<Map<String, Object>> parts =
                        (List<Map<String, Object>>) responseContent.get("parts");
                return (String) parts.get(0).get("text");
            }

            throw new OpenAiApiException("Empty response from Gemini");

        } catch (OpenAiApiException e) {
            throw e;
        } catch (Exception e) {
            throw new OpenAiApiException("Gemini API call failed: " + e.getMessage());
        }
    }

    // Prompt Builders
    private String buildWorkoutPrompt(HealthInfo h, Goal goal,
                                      List<UserDisease> diseases) {
        String diseaseList = diseases.isEmpty() ? "None" :
                diseases.stream()
                        .map(ud -> ud.getDisease().getName())
                        .collect(Collectors.joining(", "));

        return String.format("""
                You are a professional fitness trainer. Create a personalized weekly workout plan for:
                - Age: %d | Gender: %s
                - Weight: %.1f kg | Height: %.1f cm | BMI: %.1f
                - Activity Level: %s
                - Fitness Goal: %s (target value: %.1f, deadline: %s)
                - Health Conditions: %s
                
                Provide a detailed 7-day workout plan with:
                1. Daily exercises with sets, reps and rest time
                2. Diet tips based on the goal
                3. Safety notes for any health conditions
                """,
                h.getAge(), h.getGender(),
                h.getWeightKg(), h.getHeightCm(), h.getBmi(),
                h.getActivityLevel(),
                goal.getGoalType(), goal.getTargetValue(), goal.getDeadline(),
                diseaseList);
    }

    private String buildProgressPrompt(HealthInfo h, Goal goal,
                                       List<Activity> activities,
                                       WorkoutPlan latestPlan) {
        String activitySummary = activities.isEmpty()
                ? "No activities logged in the past 7 days"
                : activities.stream()
                .map(a -> String.format(
                        "  - %s: %d steps, %.0f cal, %d min workout, %.1fL water, %.1f hrs sleep",
                        a.getActivityDate(), a.getSteps(), a.getCaloriesBurned(),
                        a.getWorkoutDurationMin(), a.getWaterIntakeLiters(),
                        a.getSleepHours()))
                .collect(Collectors.joining("\n"));

        String planSummary = latestPlan != null
                ? latestPlan.getPlanContent().substring(0,
                Math.min(300, latestPlan.getPlanContent().length())) + "..."
                : "No previous workout plan";

        return String.format("""
                You are a professional fitness coach. Analyze this user's weekly progress:
                
                Goal: %s (target: %.1f, deadline: %s)
                Current BMI: %.1f | Weight: %.1f kg
                
                Previous Workout Plan (summary):
                %s
                
                Last 7 Days Activities:
                %s
                
                Please provide:
                1. Progress assessment
                2. What went well
                3. Areas for improvement
                4. Updated recommendations for next week
                """,
                goal.getGoalType(), goal.getTargetValue(), goal.getDeadline(),
                h.getBmi(), h.getWeightKg(),
                planSummary, activitySummary);
    }

    // Mapper
    private AIRecommendationResponse mapToResponse(AIRecommendation r) {
        return AIRecommendationResponse.builder()
                .id(r.getId())
                .recommendationType(r.getRecommendationType())
                .content(r.getContent())
                .generatedAt(r.getGeneratedAt())
                .build();
    }



}
