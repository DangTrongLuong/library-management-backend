package com.library.library_management_system.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReaderRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Invalid phone format")
    @Size(max = 15)
    String numberPhone;

    @Email(message = "Invalid email format")
    @Size(max = 100)
    String email;

    @Size(max = 255)
    String address;

    // optional: client may set registrationDate; if null entity's @PrePersist keeps existing behavior
    LocalDate registrationDate;
}
