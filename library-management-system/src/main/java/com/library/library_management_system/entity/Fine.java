package com.library.library_management_system.entity;

import com.library.library_management_system.enums.FineReason;
import com.library.library_management_system.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng Fine trong database
 * Quản lý các khoản phạt liên quan đến phiếu mượn
 */
@Entity
@Table(
        name = "fines",
        indexes = {
                @Index(name = "idx_fine_borrow", columnList = "borrow_id"),
                @Index(name = "idx_fine_payment_status", columnList = "payment_status"),
                @Index(name = "idx_fine_reason", columnList = "reason"),
                @Index(name = "idx_fine_date", columnList = "fine_date")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fine_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrow_id", nullable = false)
    @NotNull(message = "Borrow is required")
    Borrow borrow;

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", nullable = false, length = 50)
    @NotNull(message = "Fine reason is required")
    FineReason reason;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    BigDecimal amount;

    @Column(name = "fine_date", nullable = false)
    @NotNull(message = "Fine date is required")
    LocalDateTime fineDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 30)
    @NotNull(message = "Payment status is required")
    PaymentStatus paymentStatus;

    @Column(name = "payment_date")
    LocalDateTime paymentDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (fineDate == null) {
            fineDate = LocalDateTime.now();
        }
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.UNPAID;
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();

        // Tự động set payment date khi status chuyển sang PAID
        if (paymentStatus == PaymentStatus.PAID && paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
    }
}