package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/like")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void likePost(@RequestParam String postId) throws NotFoundException {
        likeService.likePost(postId);
    }

    @PostMapping("/unlike")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void unlikePost(@RequestParam String postId) throws NotFoundException {
        likeService.unlikePost(postId);
    }
}
