package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportPostRequest {
    private String postId;
    @NotBlank(message = "Description cannot be blank")
    private String description;
}
