package com.library.library_management_system.mapper;

import com.library.library_management_system.dto.request.ReportRequest;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.entity.Report;
import com.library.library_management_system.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReportMapper {

    // Convert Entity -> Response DTO
    public ReportResponse toResponse(Report report) {
        if (report == null) return null;

        ReportResponse resp = new ReportResponse();
        resp.setReportId(report.getReportId()); // Long
        resp.setReportType(report.getReportType());
        resp.setStartDate(report.getStartDate());
        resp.setEndDate(report.getEndDate());
        resp.setContent(report.getContent());

        // Đảm bảo creatorId là Long
        if (report.getCreator() != null && report.getCreator().getId() != null) {
            resp.setCreatorId(report.getCreator().getId());
        } else {
            resp.setCreatorId(null);
        }

        return resp;
    }

    // Convert Request DTO -> Entity
    public Report toEntity(ReportRequest request, User creator) {
        if (request == null) return null;

        Report report = new Report();
        report.setReportId(request.getReportId()); // Long
        report.setReportType(request.getReportType());
        report.setStartDate(request.getStartDate());
        report.setEndDate(request.getEndDate());
        report.setContent(request.getContent());
        report.setCreator(creator); // User entity
        report.setCreatedAt(LocalDateTime.now());

        return report;
    }

    // Extract creatorId safely
    public Long extractCreatorId(Report report) {
        return (report != null && report.getCreator() != null) ? report.getCreator().getId() : null;
    }
}