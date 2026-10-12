package com.ecommerce.inventory_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handlerResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        log.warn("Resource Not Found Exception - Path: {}, Message: {}",
                request.getDescription(false),ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.ecommerce.com/errors/resource-not-found"));

        problemDetail.setProperty("Timestamp", Instant.now());
        problemDetail.setProperty("ResourceName", ex.getResourceName());
        problemDetail.setProperty("FieldName", ex.getFieldName());
        problemDetail.setProperty("Value", ex.getMessage());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Validation Failed");

        problemDetail.setTitle("Validation Failed");
        problemDetail.setType(URI.create("https://api.ecommerce.com/errors/validation-failed"));
        problemDetail.setProperty("Timestamp", Instant.now());

        Map<String, String> errorsMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });

        problemDetail.setProperty("FieldErrors", errorsMap);

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex, WebRequest request){

        log.warn("An unexpected error has occurred{}, {}",
                request.getDescription(false),ex.getMessage(),ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please contact IT support.");

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.ecommerce.com/errors/unexpected"));
        problemDetail.setProperty("Timestamp", Instant.now());

        return problemDetail;
    }

}
