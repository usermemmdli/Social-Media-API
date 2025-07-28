package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void followUser(@RequestParam Long id) throws NotFoundException {
        followService.followUser(id);
    }

    @PostMapping("/unfollow")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void unfollowUser(@RequestParam Long id) throws NotFoundException {
        followService.unfollowUser(id);
    }
}
