package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportCommentRequest {
    private String commentId;
    @NotBlank(message = "Description cannot be blank")
    private String description;
}
