package com.library.library_management_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Integer bookId;
    String bookTitle;
    String author;
    Integer publicationYear;
    String nxb;
    Integer quantity;
    String categoryName; // Lấy từ Category.typeName
    String imageUrl;

}
