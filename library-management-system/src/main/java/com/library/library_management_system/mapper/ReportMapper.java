package com.library.library_management_system.mapper;

import org.springframework.stereotype.Component;

import com.library.library_management_system.dto.request.ReportRequest;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.entity.Report;

@Component
public class ReportMapper {

    public Report toEntity(ReportRequest request) {
        Report report = new Report();
        report.setType(request.getType());
        report.setFromDate(request.getFromDate());
        report.setToDate(request.getToDate());
        report.setContent(request.getContent());
        report.setCreatedBy(request.getCreatedBy());
        return report;
    }

    public ReportResponse toResponse(Report report) {
        ReportResponse response = new ReportResponse();
        response.setId(report.getId());
        response.setType(report.getType());
        response.setTypeDescription(report.getType().getDescription());
        response.setFromDate(report.getFromDate());
        response.setToDate(report.getToDate());
        response.setContent(report.getContent());
        response.setCreatedBy(report.getCreatedBy());
        response.setCreatedAt(report.getCreatedAt());
        response.setUpdatedAt(report.getUpdatedAt());
        return response;
    }

    public void updateEntityFromRequest(ReportRequest request, Report report) {
        if (request.getType() != null) {
            report.setType(request.getType());
        }
        if (request.getFromDate() != null) {
            report.setFromDate(request.getFromDate());
        }
        if (request.getToDate() != null) {
            report.setToDate(request.getToDate());
        }
        if (request.getContent() != null) {
            report.setContent(request.getContent());
        }
        if (request.getCreatedBy() != null) {
            report.setCreatedBy(request.getCreatedBy());
        }
    }
}