package com.prathickya.blogApp.exception;

import com.prathickya.blogApp.payload.BlogAppCustomErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

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
}
