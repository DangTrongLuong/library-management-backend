package com.library.library_management_system.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.library.library_management_system.enums.BorrowStatus;

import jakarta.validation.constraints.NotBlank;
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
public class BorrowRequest {
    String borrowId;

    @NotBlank(message = "Reader ID is required")
    String readerId;

    @NotBlank(message = "Book ID is required")
    String bookId;

    @NotNull(message = "Borrow date is required")
    LocalDate borrowDate;

    @NotNull(message = "Due date is required")
    LocalDate dueDate;

    LocalDate returnDate;

    BorrowStatus status;

    
    @NotNull(message = "Borrow price is required")
    BigDecimal borrowPrice;

    String notes;
}