package com.library.library_management_system.dto.response;

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
public class BookResponse {

    String bookId;

    String bookTitle;

    String author;

    Integer publicationYear;

    Integer categoryId;

    String categoryName;

    String nxb;

    Integer quantity;

    String imageUrl;

    BigDecimal price;
}