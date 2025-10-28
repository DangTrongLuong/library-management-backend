package com.library.library_management_system.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.library.library_management_system.enums.ReportType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {

    String id;

    ReportType type;

    String typeDescription;

    LocalDate fromDate;

    LocalDate toDate;

    String content;

    String createdBy;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}