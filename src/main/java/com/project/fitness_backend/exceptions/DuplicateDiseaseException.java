package com.project.fitness_backend.exceptions;

public class DuplicateDiseaseException extends RuntimeException {
    public DuplicateDiseaseException(String message) {
        super(message);
    }
}
