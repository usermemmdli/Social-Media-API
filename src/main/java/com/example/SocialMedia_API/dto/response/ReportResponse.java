package com.example.SocialMedia_API.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReportResponse {
    private String id;
    private String description;
    private Long reporterUserId;
    private Long userId;
    private String postId;
    private String commentId;
    private Boolean isUser;
    private Boolean isPost;
    private Boolean isComment;
    private Boolean status;
    private Date createdAt;
}
