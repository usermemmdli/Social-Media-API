package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Report;
import com.example.SocialMedia_API.dao.entity.Users;
import com.example.SocialMedia_API.dao.repository.mongo.ReportRepository;
import com.example.SocialMedia_API.dao.repository.postgres.UsersRepository;
import com.example.SocialMedia_API.dto.request.ReportCommentRequest;
import com.example.SocialMedia_API.dto.request.ReportPostRequest;
import com.example.SocialMedia_API.dto.request.ReportUserRequest;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.security.AuthenticationHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UsersRepository usersRepository;
    private final AuthenticationHelperService authHelperService;

    public void reportPost(ReportPostRequest reportPostRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Report reportPost = new Report();
        reportPost.setDescription(reportPostRequest.getDescription());
        reportPost.setReporterUserId(user.getId());
        reportPost.setUserId(null);
        reportPost.setPostId(reportPostRequest.getPostId());
        reportPost.setCommentId(null);
        reportPost.setIsUser(false);
        reportPost.setIsPost(true);
        reportPost.setIsComment(false);
        reportPost.setStatus(false);
        reportPost.setCreatedAt(Date.from(Instant.now()));
        reportRepository.save(reportPost);
    }

    public void reportComment(ReportCommentRequest reportCommentRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Report reportPost = new Report();
        reportPost.setDescription(reportCommentRequest.getDescription());
        reportPost.setReporterUserId(user.getId());
        reportPost.setUserId(null);
        reportPost.setPostId(null);
        reportPost.setCommentId(reportCommentRequest.getCommentId());
        reportPost.setIsUser(false);
        reportPost.setIsPost(false);
        reportPost.setIsComment(true);
        reportPost.setStatus(false);
        reportPost.setCreatedAt(Date.from(Instant.now()));
        reportRepository.save(reportPost);
    }

    public void reportUser(ReportUserRequest reportUserRequest) throws NotFoundException {
        Users user = usersRepository.findByUsername(authHelperService.getCurrentUsername())
                .orElseThrow(() -> new NotFoundException("User not found with username: " + authHelperService.getCurrentUsername()));

        Report reportPost = new Report();
        reportPost.setDescription(reportUserRequest.getDescription());
        reportPost.setReporterUserId(user.getId());
        reportPost.setUserId(reportPost.getUserId());
        reportPost.setPostId(null);
        reportPost.setCommentId(null);
        reportPost.setIsUser(true);
        reportPost.setIsPost(false);
        reportPost.setIsComment(false);
        reportPost.setStatus(false);
        reportPost.setCreatedAt(Date.from(Instant.now()));
        reportRepository.save(reportPost);
    }
}
