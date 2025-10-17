package com.library.library_management_system.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LibrarianRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String librarianName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Invalid phone format")
    String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Size(max = 100, message = "Email must be at most 100 characters")
    String email;

    @NotNull(message = "Shift is required")
    Integer shiftId;

    @NotNull(message = "Hourly wage is required")
    @DecimalMin(value = "0.0", message = "Hourly wage must be >= 0")
    BigDecimal hourlyWage;

    @Size(max = 10, message = "Gender must be at most 10 characters")
    String gender;

    LocalDate startDate;

    @Size(max = 255, message = "Address must be at most 255 characters")
    String address;

    @Size(max = 20, message = "Status must be at most 20 characters")
    String status;

    String notes;
}