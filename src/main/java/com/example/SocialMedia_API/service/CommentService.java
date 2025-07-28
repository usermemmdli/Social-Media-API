package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Comment;
import com.example.SocialMedia_API.dao.entity.Post;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.CommentRepository;
import com.example.SocialMedia_API.dao.repository.mongo.PostRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.request.CommentRequest;
import com.example.SocialMedia_API.exception.BadRequestException;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UsersRepository usersRepository;
    private final PostRepository postRepository;
    private final AuthenticationHelperService authHelperService;

    public void createNewComment(CommentRequest commentRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Comment comment = new Comment();
        comment.setPostId(commentRequest.getPostId());
        comment.setUserId(user.getId());
        comment.setContent(commentRequest.getContent());
        comment.setCreatedAt(Date.from(Instant.now()));
        commentRepository.save(comment);
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + commentRequest.getPostId()));
        post.setCommentCount(post.getCommentCount() + 1);
        postRepository.save(post);
    }

    public void deleteComment(String id) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment not found with id: " + id));

        if (!comment.getUserId().equals(user.getId())) {
            throw new BadRequestException("You can delete only your own comments");
        }
        commentRepository.deleteById(id);
        commentRepository.save(comment);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
        post.setCommentCount(post.getCommentCount() - 1);
        postRepository.save(post);
    }
}
