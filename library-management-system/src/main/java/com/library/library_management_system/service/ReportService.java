package com.library.library_management_system.service;

import com.library.library_management_system.dto.request.ReportRequest;
import com.library.library_management_system.dto.response.ReportResponse;
import com.library.library_management_system.entity.Report;
import com.library.library_management_system.entity.User;
import com.library.library_management_system.exception.NotFoundException;
import com.library.library_management_system.mapper.ReportMapper;
import com.library.library_management_system.repository.ReportRepository;
import com.library.library_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final ReportMapper reportMapper;

    public ReportService(ReportRepository reportRepository, UserRepository userRepository, ReportMapper reportMapper) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.reportMapper = reportMapper;
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> getAllReports() {
        return reportRepository.findAll()
                .stream()
                .map(reportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReportResponse getReportById(Long id) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Report not found with id: " + id));
        return reportMapper.toResponse(report);
    }

    @Transactional(readOnly = true)
    public List<ReportResponse> searchReports(String type, Long creatorId) {
        return reportRepository.findAll().stream()
                .filter(r -> {
                    if (type != null && !type.isBlank()) {
                        if (r.getReportType() == null || !r.getReportType().toLowerCase().contains(type.toLowerCase()))
                            return false;
                    }
                    if (creatorId != null) {
                        return r.getCreator() != null && r.getCreator().getId().equals(creatorId);
                    }
                    return true;
                })
                .map(reportMapper::toResponse)
                .collect(Collectors.toList());
    }

    //Them bc
    @Transactional
    public ReportResponse createReport(ReportRequest request) {
        if (request.getReportId() != null && reportRepository.existsById(request.getReportId())) {
            throw new RuntimeException("Report ID already exists: " + request.getReportId());
        }

        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new NotFoundException("Creator not found with id: " + request.getCreatorId()));

        Report report = reportMapper.toEntity(request, creator);
        Report savedReport = reportRepository.save(report);
        return reportMapper.toResponse(savedReport);
    }


}