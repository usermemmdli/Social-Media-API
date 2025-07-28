package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Like;
import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.LikeRepository;
import com.example.SocialMedia_API.dao.repository.mongo.PostRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.request.CreatePostRequest;
import com.example.SocialMedia_API.dto.request.PostEditRequest;
import com.example.SocialMedia_API.dto.response.PostResponse;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.mapper.PostMapper;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UsersRepository usersRepository;
    private final LikeRepository likeRepository;
    private final AuthenticationHelperService authHelperService;

    public PostPageResponse getUsersPosts(int page, int count) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Pageable pageable = PageRequest.of(page, count);
        Page<Post> userPosts = postRepository.findByUserId(user.getId(), pageable);
        List<PostResponse> postResponseList = userPosts.getContent().stream()
                .map(postMapper::toPostResponse)
                .toList();

        return new PostPageResponse(
                postResponseList,
                userPosts.getTotalElements(),
                userPosts.getTotalPages(),
                userPosts.hasNext());
    }

    public void createNewPost(CreatePostRequest postRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));
        Post post = new Post();
        post.setUserId(user.getId());
        post.setCommentId(new ArrayList<>());
        post.setContent(postRequest.getContent());
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setImageUrl(postRequest.getImageUrl());
        post.setCreatedAt(Date.from(Instant.now()));
        postRepository.save(post);
        Like like = new Like();
        like.setPostId(post.getId());
        like.setUserId(new ArrayList<>());
        likeRepository.save(like);
    }

    public void editPost(PostEditRequest postEditRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Post editedPost = postRepository.findById(postEditRequest.getId())
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postEditRequest.getId()));

        if (!editedPost.getUserId().equals(user.getId())) {
            throw new BadRequestException("You can edit only your own posts");
        }

        editedPost.setContent(postEditRequest.getContent());
        editedPost.setImageUrl(postEditRequest.getImageUrl());
        editedPost.setUpdatedAt(Date.from(Instant.now()));
        postRepository.save(editedPost);
    }

    public void deletePost(String id) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Post deletedPost = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));

        if (!deletedPost.getUserId().equals(user.getId())) {
            throw new BadRequestException("You can delete only your own posts");
        }
        postRepository.deleteById(id);
        likeRepository.deleteByPostId(deletedPost.getId());
    }
}
