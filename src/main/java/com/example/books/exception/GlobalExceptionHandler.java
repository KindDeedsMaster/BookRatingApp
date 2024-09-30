package com.example.books.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException exception) {
        ProblemDetail response = ProblemDetail.forStatus(404);
        response.setTitle("Entity Not Found");
        response.setStatus(response.getStatus());
        response.setDetail(exception.getMessage());
        return response;
    }
}