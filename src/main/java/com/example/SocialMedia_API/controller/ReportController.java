package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.ReportCommentRequest;
import com.example.SocialMedia_API.dto.request.ReportPostRequest;
import com.example.SocialMedia_API.dto.request.ReportUserRequest;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/posts/report")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void reportPost(@RequestBody ReportPostRequest reportPostRequest) throws NotFoundException {
        reportService.reportPost(reportPostRequest);
    }

    @PostMapping("/comment/report")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void reportComment(@RequestBody ReportCommentRequest reportCommentRequest) throws NotFoundException {
        reportService.reportComment(reportCommentRequest);
    }

    @PostMapping("/report")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public void reportUser(@RequestBody ReportUserRequest reportUserRequest) throws NotFoundException {
        reportService.reportUser(reportUserRequest);
    }
}
