package com.example.SocialMedia_API.exception.handler;

import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.EmailAlreadyIsTakenException;
import com.example.SocialMedia_API.exception.InvalidEmailOrPasswordException;
import com.example.SocialMedia_API.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
            NotFoundException.class, HttpStatus.NOT_FOUND,
            EmailAlreadyIsTakenException.class, HttpStatus.BAD_REQUEST,
            BadRequestException.class, HttpStatus.BAD_REQUEST,
            InvalidEmailOrPasswordException.class, HttpStatus.BAD_REQUEST
    );

    @ExceptionHandler({
            NotFoundException.class,
            EmailAlreadyIsTakenException.class,
            BadRequestException.class,
            InvalidEmailOrPasswordException.class
    })
    public ResponseEntity<Map<String, Object>> handleCustomExceptions(Exception e) {
        HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(e.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", e.getMessage());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, status);
    }
}
