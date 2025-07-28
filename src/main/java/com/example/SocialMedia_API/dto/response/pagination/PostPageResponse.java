package com.example.SocialMedia_API.dto.response.pagination;

import com.example.SocialMedia_API.dto.response.PostResponse;

import java.util.List;

public record PostPageResponse(
        List<PostResponse> postResponse,
        long totalElements,
        int totalPages,
        boolean hasNextPages) {
}
