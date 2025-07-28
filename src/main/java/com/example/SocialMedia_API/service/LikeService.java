package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Like;
import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.LikeRepository;
import com.example.SocialMedia_API.dao.repository.mongo.PostRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final AuthenticationHelperService authHelperService;

    public void likePost(String postId) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Like like = likeRepository.findByPostId(postId)
                .orElseThrow(() -> new NotFoundException("Like not found with post id: " + postId));

        if (like.getUserId().contains(user.getId())) {
            throw new BadRequestException("You can like only once");
        }
        like.setPostId(postId);
        like.getUserId().add(user.getId());
        likeRepository.save(like);
        user.getLikedPostsId().add(postId);
        usersRepository.save(user);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    public void unlikePost(String postId) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Like like = likeRepository.findByPostId(postId)
                .orElseThrow(() -> new NotFoundException("Like not found with post id: " + postId));

        if (!like.getUserId().contains(user.getId())) {
            throw new BadRequestException("You can unlike only liked posts");
        }
        like.getUserId().remove(user.getId());
        likeRepository.save(like);
        user.getLikedPostsId().remove(postId);
        usersRepository.save(user);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + postId));
        post.setLikeCount(post.getLikeCount() - 1);
        postRepository.save(post);
    }
}
