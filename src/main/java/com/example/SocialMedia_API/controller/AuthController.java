package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.LoginRequest;
import com.example.SocialMedia_API.dto.request.SignUpRequest;
import com.example.SocialMedia_API.dto.response.JwtResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        JwtResponse jwtResponse = authService.loginUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUpUser(@RequestBody @Valid SignUpRequest signUpRequest) throws NotFoundException {
        authService.signUpUser(signUpRequest);
    }
}
