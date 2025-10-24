package com.library.library_management_system.dto.request;

import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Request cho Fine
 * Dùng để tạo mới hoặc cập nhật thông tin phạt
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FineRequest {

    @NotBlank(message = "Borrow ID is required")
    String borrowId;

    @NotNull(message = "Fine reason is required")
    FineReason reason;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    BigDecimal amount;

    LocalDateTime fineDate;

    @NotNull(message = "Payment status is required")
    PaymentStatus paymentStatus;

    LocalDateTime paymentDate;

    String notes;
}