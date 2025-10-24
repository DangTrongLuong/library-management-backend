package com.library.library_management_system.enums;

/**
 * Enum định nghĩa trạng thái thanh toán
 * Compatible with Spring Boot 3.5.6 + Java 25
 */
public enum PaymentStatus {
    PAID("Paid", "Đã thanh toán"),
    UNPAID("Unpaid", "Chưa thanh toán"),
    PARTIALLY_PAID("Partially Paid", "Thanh toán một phần");

    private final String code;
    private final String description;

    PaymentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}