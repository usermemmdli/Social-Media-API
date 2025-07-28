package com.example.SocialMedia_API.mapper;

import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dto.response.UsersResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class UsersMapper {
    public UsersResponse toUsersResponse(Users users) {
        return UsersResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .profilePhotoUrl(users.getProfilePhotoUrl())
                .build();
    }
}
