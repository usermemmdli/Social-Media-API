package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.BookmarksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class BookmarksController {
    private final BookmarksService bookmarksService;

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
