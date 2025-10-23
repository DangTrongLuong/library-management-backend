package com.library.library_management_system.entity;

import java.time.LocalDate;

import com.library.library_management_system.enums.BorrowStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(
    name = "borrows",
    indexes = {
        @Index(name = "idx_borrow_reader", columnList = "reader_id"),
        @Index(name = "idx_borrow_book", columnList = "book_id"),
        @Index(name = "idx_borrow_date", columnList = "borrow_date"),
        @Index(name = "idx_borrow_status", columnList = "status")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Borrow {

    @Id
    @Column(name = "borrow_id", nullable = false, unique = true, length = 9)
    String borrowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id", nullable = false)
    @NotNull(message = "Reader is required")
    Reader reader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false) 
    @NotNull(message = "Book is required")
    Books book;

    @NotNull(message = "Borrow date is required")
    @Column(name = "borrow_date", nullable = false)
    LocalDate borrowDate;

    @NotNull(message = "Due date is required")
    @Column(name = "due_date", nullable = false)
    LocalDate dueDate;

    @Column(name = "return_date")
    LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    BorrowStatus status;

    @Column(name = "notes", columnDefinition = "TEXT")
    String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        if (borrowDate == null) {
            borrowDate = LocalDate.now();
        }
        if (status == null) {
            status = BorrowStatus.BORROWED;
        }
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
        
        if (returnDate != null) {
            status = BorrowStatus.RETURNED;
        } else if (LocalDate.now().isAfter(dueDate)) {
            status = BorrowStatus.OVERDUE;
        }
    }
}