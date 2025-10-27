package com.library.library_management_system.dto.request;

import com.library.library_management_system.customValidation.FutureOrPresentYear;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {

    @NotBlank(message = "Book title is required")
    @Size(max = 255, message = "Book title must be at most 255 characters")
    String bookTitle;

    @NotBlank(message = "Author is required")
    @Size(max = 100, message = "Author must be at most 100 characters")
    String author;

    @NotNull(message = "Publication year is required")
    @Min(value = 1500, message = "Publication year must be >= 1500")
    @FutureOrPresentYear
    Integer publicationYear;

    @NotNull(message = "Category is required")
    Integer categoryId;

    @Size(max = 100, message = "Publisher must be at most 100 characters")
    String nxb;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be >= 0")
    Integer quantity;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    BigDecimal price;
}