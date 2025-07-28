package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDeleteRequest {
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
