package com.library.library_management_system.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    String reportId;
    String reportType;
    LocalDate startDate;
    LocalDate endDate;
    String content;
    String creatorId;
    LocalDate createdAt;
    LocalDate updatedAt;
}
