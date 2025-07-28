package com.example.SocialMedia_API.mapper;

import com.example.SocialMedia_API.dao.entity.Report;
import com.example.SocialMedia_API.dto.response.ReportResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public class ReportMapper {
    public ReportResponse toReportResponse(Report report) {
        return ReportResponse.builder()
                .id(report.getId())
                .description(report.getDescription())
                .reporterUserId(report.getReporterUserId())
                .userId(report.getUserId())
                .postId(report.getPostId())
                .commentId(report.getCommentId())
                .isUser(report.getIsUser())
                .isPost(report.getIsPost())
                .isComment(report.getIsComment())
                .status(report.getStatus())
                .createdAt(report.getCreatedAt())
                .build();
    }
}
