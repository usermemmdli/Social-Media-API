package com.example.SocialMedia_API.service;

import com.example.SocialMedia_API.dao.entity.Report;
import com.example.SocialMedia_API.dao.repository.mongo.ReportRepository;
import com.example.SocialMedia_API.dto.request.CheckReportRequest;
import com.example.SocialMedia_API.dto.response.ReportResponse;
import com.example.SocialMedia_API.exception.NotFoundException;
import com.example.SocialMedia_API.mapper.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    public List<ReportResponse> getPostReport() {
        return reportRepository.findAll().stream()
                .filter(report -> report.getIsPost() == true)
                .map(reportMapper::toReportResponse)
                .toList();
    }

    public List<ReportResponse> getCommentReport() {
        return reportRepository.findAll().stream()
                .filter(report -> report.getIsComment() == true)
                .map(reportMapper::toReportResponse)
                .toList();
    }

    public List<ReportResponse> getUsersReport() {
        return reportRepository.findAll().stream()
                .filter(report -> report.getIsUser() == true)
                .map(reportMapper::toReportResponse)
                .toList();
    }

    public void checkPostReport(CheckReportRequest checkReportRequest) throws NotFoundException {
        Report postReport = reportRepository.findById(checkReportRequest.getId())
                .orElseThrow(() -> new NotFoundException("Report not found with id: " + checkReportRequest.getId()));
        postReport.setStatus(checkReportRequest.getStatus());
        postReport.setUpdatedAt(Date.from(Instant.now()));
        reportRepository.save(postReport);
    }
}
