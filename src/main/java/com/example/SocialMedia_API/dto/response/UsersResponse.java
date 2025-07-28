package com.example.SocialMedia_API.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersResponse {
    private Long id;
    private String username;
    private String profilePhotoUrl;
}
