package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.PostRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.response.PostResponse;
import com.example.SocialMedia_API.dto.response.UsersResponse;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.mapper.PostMapper;
import com.example.SocialMedia_API.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExploreService {
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final UsersMapper usersMapper;
    private final PostMapper postMapper;

    public PostPageResponse getExplorePosts(int page, int count) {
        Pageable pageable = PageRequest.of(page, count);
        Page<Post> userPosts = postRepository.findAll(pageable);
        List<PostResponse> postResponseList = userPosts.getContent().stream()
                .filter(post -> post.getLikeCount() > 10 || post.getCommentCount() > 5)
                .map(postMapper::toPostResponse)
                .toList();

        return new PostPageResponse(
                postResponseList,
                userPosts.getTotalElements(),
                userPosts.getTotalPages(),
                userPosts.hasNext());
    }

    public UsersResponse getUsers(String username) throws NotFoundException {
        Users users = usersRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username.toLowerCase()));
        return usersMapper.toUsersResponse(users);
    }
}
