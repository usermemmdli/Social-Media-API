package com.example.SocialMedia_API.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PostResponse {
    private String id;
    private Long userId;
    private String content;
    private String imageUrl;
    private Integer commentCount;
    private Integer likeCount;
    private Date createdAt;
}
