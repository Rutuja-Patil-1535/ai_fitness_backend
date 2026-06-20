package com.project.fitness_backend.exceptions;

public class OpenAiApiException extends RuntimeException {
    public OpenAiApiException(String message) {
        super(message);
    }
}
