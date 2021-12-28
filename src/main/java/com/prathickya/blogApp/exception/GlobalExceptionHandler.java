package com.prathickya.blogApp.exception;

import com.prathickya.blogApp.payload.BlogAppCustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BlogAppCustomErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            WebRequest webRequest) {
        BlogAppCustomErrorResponse errorResponse = new BlogAppCustomErrorResponse(
                LocalDateTime.now(), resourceNotFoundException.getMessage(),
                webRequest.getDescription(Boolean.FALSE));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<BlogAppCustomErrorResponse> handleBlogAPIException(
            BlogAPIException blogAPIException,
            WebRequest webRequest) {
        BlogAppCustomErrorResponse errorResponse = new BlogAppCustomErrorResponse(
                LocalDateTime.now(), blogAPIException.getMessage(),
                webRequest.getDescription(Boolean.FALSE));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BlogAppCustomErrorResponse> handleGlobalException(
            Exception exception,
            WebRequest webRequest) {
        BlogAppCustomErrorResponse errorResponse = new BlogAppCustomErrorResponse(
                LocalDateTime.now(), exception.getMessage(),
                webRequest.getDescription(Boolean.FALSE));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            final String fieldName = ((FieldError) error).getField();
            final String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
