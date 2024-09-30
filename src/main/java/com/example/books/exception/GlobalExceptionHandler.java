package com.example.books.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException exception){
        ProblemDetail response = ProblemDetail.forStatus(400);
        response.setTitle("Validation Error");
        response.setStatus(response.getStatus());
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        String detailMessage = violations.stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .collect(Collectors.joining("; "));
        response.setDetail(detailMessage);
        return response;
    }
}