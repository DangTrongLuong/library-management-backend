package com.library.library_management_system.service;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.entity.Report;
import com.library.library_management_system.mapper.ReportMapper;
import com.library.library_management_system.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final ReportRepository repository;

    @Autowired
    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public List<ReportResponse> getAllReports() {
        return repository.findAll()
                .stream()
                .map(ReportMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ReportResponse getReportById(String id) {
        return repository.findById(id).map(ReportMapper::toResponse).orElse(null);
    }
}
