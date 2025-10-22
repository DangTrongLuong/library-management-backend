package com.library.library_management_system.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReaderRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Phone must be 10-11 digits starting with 0")
    String numberPhone;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be at most 100 characters")
    String email;

    @Size(max = 255, message = "Address must be at most 255 characters")
    String address;

    LocalDate registrationDate;

    @NotNull(message = "Card type is required")
    CardType cardType;
}