package com.project.fitness_backend.service;

import com.project.fitness_backend.dto.AuthResponse;
import com.project.fitness_backend.dto.LoginRequest;
import com.project.fitness_backend.dto.RegisterRequest;
import com.project.fitness_backend.dto.UserResponse;
import com.project.fitness_backend.enums.Role;
import com.project.fitness_backend.exceptions.EmailAlreadyExistsException;
import com.project.fitness_backend.model.User;
import com.project.fitness_backend.repository.UserRepo;
import com.project.fitness_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public UserResponse register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email already exist : "+request.getEmail());
        }
        User user=User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(true)
                .build();
        userRepository.save(user);
        UserResponse response=UserResponse.builder()
                .id(Long.valueOf(String.valueOf(user.getUserId())))
                .active(user.isActive())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .build();
        return response;
    }
    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("Use Not Found"));
        String token= jwtService.generateToken(user);
        return new AuthResponse(token,user.getRole().name());
    }

}
