package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequest {
    @Pattern(regexp = "^[a-z0-9]+$", message = "Username only lower case")
    @NotBlank(message = "Username cannot be blank")
    private String username;
    private String fullName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "Email cannot be blank")
    private String email;
    private String profilePhotoUrl;
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
