package com.project.fitness_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkOutTemplateRequest {
    @NotBlank(message = "Name not be blank")
    public String name;
    @NotBlank(message = "Please Enter some content here")
    public String content;
}
