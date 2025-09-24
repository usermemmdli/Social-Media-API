package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.response.pagination.PostPageResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.BookmarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class BookmarksController {
    private final BookmarksService bookmarksService;

    @GetMapping("/account/bookmarks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PostPageResponse> getMarkedPosts(@RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "count", defaultValue = "20") int count) throws NotFoundException {
        PostPageResponse postPageResponse = bookmarksService.getMarkedPosts(page, count);
        return ResponseEntity.ok(postPageResponse);
    }

    @PostMapping("/posts/bookmarks")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBookmarks(@RequestParam String postId) throws NotFoundException {
        bookmarksService.addBookmarks(postId);
    }

    @PostMapping("/posts/bookmarks/delete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookmarks(@RequestParam String postId) throws NotFoundException {
        bookmarksService.deleteBookmarks(postId);
    }
}
