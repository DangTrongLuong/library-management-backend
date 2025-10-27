package com.library.library_management_system.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    private Long reportId;      // ID của báo cáo
    private String reportType;  // Loại báo cáo
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    private Long creatorId;     // ID của người tạo
}