package com.library.library_management_system.dto.response;

import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Response cho Fine
 * Dùng để trả về thông tin phạt cho client
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineResponse {

    Long id;

    // Thông tin Borrow
    String borrowId;
    String readerName;
    String bookTitle;

    // Thông tin Fine
    FineReason reason;
    String reasonDescription;
    BigDecimal amount;
    LocalDateTime fineDate;
    PaymentStatus paymentStatus;
    String paymentStatusDescription;
    LocalDateTime paymentDate;
    String notes;

    // Audit fields
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}