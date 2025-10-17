package com.library.library_management_system.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.library.library_management_system.enums.Shift;

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

    Shift shift;

    String timeShift;

    BigDecimal hourlyWage;

    BigDecimal salary;

    String gender;

    LocalDate startDate;

    String address;

    String status;

    String notes;

    LocalDate createdAt;

    LocalDate updatedAt;
}