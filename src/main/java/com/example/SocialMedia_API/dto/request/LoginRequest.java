package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Username or email cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
