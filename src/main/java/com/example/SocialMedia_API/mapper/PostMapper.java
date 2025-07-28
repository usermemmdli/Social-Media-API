package com.example.SocialMedia_API.mapper;

import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dto.response.PostResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class PostMapper {
    public PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .userId(post.getUserId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .commentCount(post.getCommentCount())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
