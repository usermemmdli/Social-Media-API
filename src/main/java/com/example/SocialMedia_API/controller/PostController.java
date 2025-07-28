package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.CreatePostRequest;
import com.example.SocialMedia_API.dto.request.PostEditRequest;
import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostPageResponse> getUsersPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                          @RequestParam(value = "count", defaultValue = "9") int count) throws NotFoundException {
        PostPageResponse postResponse = postService.getUsersPosts(page, count);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping("/posts/new")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewPost(@RequestBody CreatePostRequest postRequest) throws NotFoundException {
        postService.createNewPost(postRequest);
    }

    @PutMapping("/posts/edit")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void editPost(@RequestBody PostEditRequest postEditRequest) throws NotFoundException {
        postService.editPost(postEditRequest);
    }

    @DeleteMapping("/posts")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@RequestParam String id) throws NotFoundException {
        postService.deletePost(id);
    }
}
