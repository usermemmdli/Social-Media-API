package com.example.SocialMedia_API.dto.response;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AccountResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String profilePhotoUrl;
    private Integer countFollowersId;
    private Integer countFollowingId;
    private Timestamp createdAt;
}
