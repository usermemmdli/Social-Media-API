package com.example.SocialMedia_API.mapper;

import com.example.SocialMedia_API.dao.entity.Story;
import com.example.SocialMedia_API.dto.response.StoryResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class StoryMapper {
    public StoryResponse toStoryResponse(Story story) {
        return StoryResponse.builder()
                .id(story.getId())
                .content(story.getContent())
                .imageUrl(story.getImageUrl())
                .createdAt(story.getCreatedAt())
                .build();
    }
}
