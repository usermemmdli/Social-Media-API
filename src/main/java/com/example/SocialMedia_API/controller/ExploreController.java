package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.response.UsersResponse;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.ExploreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ExploreController {
    private final ExploreService exploreService;

    @GetMapping("/explore")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostPageResponse> getExplorePosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                            @RequestParam(value = "count", defaultValue = "9") int count) {
        PostPageResponse postResponse = exploreService.getExplorePosts(page, count);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/explore/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UsersResponse> getUsers(@PathVariable String username) throws NotFoundException {
        UsersResponse usersResponse = exploreService.getUsers(username);
        return ResponseEntity.ok(usersResponse);
    }
}
