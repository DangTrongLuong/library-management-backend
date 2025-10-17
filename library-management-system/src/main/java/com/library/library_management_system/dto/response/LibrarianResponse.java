package com.library.library_management_system.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibrarianResponse {

    String librarianId;

    String librarianName;

    String phone;

    String email;

    Integer shiftId;

    String shiftName;

    String timeShift;

    BigDecimal hoursPerDay;

    BigDecimal hourlyWage;

    YearMonth salaryMonth;

    BigDecimal totalSalary;

    String gender;

    LocalDate startDate;

    String address;

    String status;

    String notes;

    LocalDate createdAt;

    LocalDate updatedAt;
}