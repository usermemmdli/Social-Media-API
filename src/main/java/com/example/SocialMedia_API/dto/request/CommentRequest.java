package com.example.SocialMedia_API.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {
    private String postId;
    @NotNull(message = "Content cannot be blank")
    private String content;
}
