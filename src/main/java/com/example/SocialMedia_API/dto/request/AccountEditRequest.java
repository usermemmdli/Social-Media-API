package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountEditRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    private String fullName;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    private String profilePhotoUrl;
}
