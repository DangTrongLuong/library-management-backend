package com.library.library_management_system.enums;

/**
 * Enum định nghĩa các loại lý do phạt
 * Compatible with Spring Boot 3.5.6 + Java 25
 */
public enum FineReason {
    LOST_BOOK("Lost Book", "Mất sách"),
    DAMAGED_BOOK("Damaged Book", "Hỏng sách"),
    OVERDUE("Overdue", "Quá hạn");

    private final String code;
    private final String description;

    FineReason(String code, String description) {
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