package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostEditRequest {
    @NotBlank(message = "Id cannot be blank")
    private String id;
    @NotBlank(message = "Content cannot be blank")
    private String content;
    private String imageUrl;
}
