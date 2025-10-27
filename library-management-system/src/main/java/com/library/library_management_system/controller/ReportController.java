package com.library.library_management_system.controller;

import com.library.library_management_system.dto.request.ReportRequest;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Lấy tất cả báo cáo
    @GetMapping
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<ReportResponse> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    // Lấy báo cáo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable Long id) {
        ReportResponse report = reportService.getReportById(id);
        return ResponseEntity.ok(report);
    }

    // Tìm kiếm báo cáo theo loại và người tạo
    @GetMapping("/search")
    public ResponseEntity<List<ReportResponse>> searchReports(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Long creatorId) {
        List<ReportResponse> reports = reportService.searchReports(type, creatorId);
        return ResponseEntity.ok(reports);
    }

    // Tạo mới báo cáo
    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@RequestBody ReportRequest request) {
        ReportResponse report = reportService.createReport(request);
        return ResponseEntity.ok(report);
    }


}