package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.CreateStoryRequest;
import com.example.SocialMedia_API.dto.response.StoryResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @GetMapping("/story")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StoryResponse> showMyStory() throws NotFoundException {
        StoryResponse storyResponse = storyService.showMyStory();
        return ResponseEntity.ok(storyResponse);
    }

    @PostMapping("/story/new")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StoryResponse> createNewStory(@RequestBody CreateStoryRequest createStoryRequest) throws NotFoundException {
        StoryResponse storyResponse = storyService.createNewStory(createStoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(storyResponse);
    }

    @DeleteMapping("/story/delete")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStory(@RequestParam String id) throws NotFoundException {
        storyService.deleteStory(id);
    }
}
