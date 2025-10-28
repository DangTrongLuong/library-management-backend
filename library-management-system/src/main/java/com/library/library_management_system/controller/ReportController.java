package com.library.library_management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.library.library_management_system.dto.request.ReportRequest;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/getAllReport")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        return ResponseEntity.ok(reportService.getAllReports());
    }

    @GetMapping("/getReport/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable String id) {
        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @PostMapping("/createReport")
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody ReportRequest request) {
        return ResponseEntity.ok(reportService.createReport(request));
    }

    @PutMapping("/updateReport/{id}")
    public ResponseEntity<ReportResponse> updateReport(@PathVariable String id,
            @Valid @RequestBody ReportRequest request) {
        return ResponseEntity.ok(reportService.updateReport(id, request));
    }

    @DeleteMapping("/deleteReport/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReportResponse>> searchReports(@RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(reportService.searchReports(keyword));
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ReportResponse>> getReportsPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fromDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(reportService.getReportsPaged(page, size, sortBy, sortDir));
    }

    @GetMapping("/searchPaged")
    public ResponseEntity<Page<ReportResponse>> searchReportsPaged(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fromDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok(reportService.searchReportsPaged(keyword, page, size, sortBy, sortDir));
    }
}