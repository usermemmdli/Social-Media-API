package com.example.SocialMedia_API.controller;

import com.example.SocialMedia_API.dto.request.CheckReportRequest;
import com.example.SocialMedia_API.dto.response.ReportResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/post-report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponse>> getPostReport() {
        List<ReportResponse> reportResponse = adminService.getPostReport();
        return ResponseEntity.ok(reportResponse);
    }

    @GetMapping("/comment-report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponse>> getCommentReport() {
        List<ReportResponse> reportResponse = adminService.getCommentReport();
        return ResponseEntity.ok(reportResponse);
    }

    @GetMapping("/users-report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponse>> getUsersReport() {
        List<ReportResponse> reportResponse = adminService.getUsersReport();
        return ResponseEntity.ok(reportResponse);
    }

    @PatchMapping("/check-report")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void checkPostReport(@RequestBody CheckReportRequest checkReportRequest) throws NotFoundException {
        adminService.checkPostReport(checkReportRequest);
    }
}
