package com.library.library_management_system.dto.response;

import java.time.LocalDate;

import com.library.library_management_system.enums.BorrowStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BorrowResponse {

    String borrowId;

    
    String readerId;
    String readerName;
    String readerPhone;
    String readerEmail;

    
    String bookId;
    String bookTitle;
    String bookAuthor;
    String categoryName;
    String imageUrl;

    LocalDate borrowDate;

    LocalDate dueDate;

    LocalDate returnDate;

    BorrowStatus status;

    String notes;

    LocalDate createdAt;

    LocalDate updatedAt;
}