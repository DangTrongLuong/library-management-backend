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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineResponse {

    Long id;

    String borrowId;
    String readerId;
    String readerName;
    String bookId;
    String bookTitle;

    FineReason reason;
    String reasonDescription;
    BigDecimal amount;
    LocalDateTime fineDate;
    PaymentStatus paymentStatus;
    String paymentStatusDescription;
    LocalDateTime paymentDate;
    String notes;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}