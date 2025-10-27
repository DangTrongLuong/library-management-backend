package com.library.library_management_system.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long reportId;
    private String reportType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    private Long creatorId; // để hiển thị thông tin người tạo
}