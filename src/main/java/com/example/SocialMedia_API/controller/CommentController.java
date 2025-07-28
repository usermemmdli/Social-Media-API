package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.CommentRequest;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments/new")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewComment(@RequestBody CommentRequest commentRequest) throws NotFoundException {
        commentService.createNewComment(commentRequest);
    }

    @DeleteMapping("/comments/delete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@RequestParam String id) throws NotFoundException {
        commentService.deleteComment(id);
    }
}
