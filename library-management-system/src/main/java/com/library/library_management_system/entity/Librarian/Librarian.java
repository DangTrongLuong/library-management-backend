package com.library.library_management_system.entity.Librarian;

import java.time.LocalDate;

import com.library.library_management_system.enums.Shift;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Librarian",
       indexes = {
           @Index(name = "idx_librarian_phone", columnList = "phone"),
           @Index(name = "idx_librarian_email", columnList = "email"),
           @Index(name = "idx_librarian_shift_id", columnList = "shift_id"),
           @Index(name = "idx_librarian_status", columnList = "status")
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Librarian {

    @Id
    @Size(max = 8, message = "Librarian ID must be at most 8 characters")
    @Column(name = "librarian_id", nullable = false, unique = true, length = 8)
    String librarianId;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    @Column(name = "librarian_name", nullable = false)
    String librarianName;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Invalid phone format")
    @Column(name = "phone", nullable = false, unique = true, length = 15)
    String phone;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true)
    String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shift_id", nullable = false)
    Shift shift;

    @Column(name = "gender", length = 10)
    String gender;

    @Column(name = "start_date")
    LocalDate startDate;

    @Column(name = "address", length = 255)
    String address;

    @Column(name = "status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'ACTIVE'")
    String status = "ACTIVE";

    @Column(name = "notes", columnDefinition = "TEXT")
    String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}