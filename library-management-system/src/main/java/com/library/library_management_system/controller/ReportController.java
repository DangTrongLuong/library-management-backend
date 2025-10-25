package com.library.library_management_system.controller;

import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Hàm lấy tất cả báo cáo
    @GetMapping("/getAllReport")
    public ResponseEntity<List<ReportResponse>> getAllReports() {
        List<ReportResponse> list = reportService.getAllReports();
        if (list == null || list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    // Hàm lấy báo cáo theo ID
    @GetMapping("/getReport/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable("id") String id) {
        ReportResponse resp = reportService.getReportById(id);
        if (resp == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
