package com.example.SocialMedia_API.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class StoryResponse {
    private String id;
    private String content;
    private String imageUrl;
    private Date createdAt;
}
