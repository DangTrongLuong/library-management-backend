package com.library.library_management_system.dto.request;
import java.time.LocalDate;

import com.library.library_management_system.enums.ReportType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest {
@NotNull(message = "Report type is required")
ReportType type;
@NotNull(message = "From date is required")
LocalDate fromDate;
@NotNull(message = "To date is required")
LocalDate toDate;
String content;
@NotNull(message = "Created by is required")
String createdBy;
}