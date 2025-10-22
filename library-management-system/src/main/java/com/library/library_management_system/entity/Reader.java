package com.library.library_management_system.entity;

import com.library.library_management_system.enums.CardType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(
        name = "readers",
        indexes = {
                @Index(name = "idx_reader_phone", columnList = "numberPhone"),
                @Index(name = "idx_reader_email", columnList = "email"),
                @Index(name = "idx_reader_card_type", columnList = "cardType")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reader {

    @Id
    @Size(max = 8, message = "Reader ID must be at most 8 characters")
    @Column(name = "reader_id", nullable = false, unique = true, length = 8)
    String readerId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Invalid phone format")
    @Column(name = "numberPhone", nullable = false, unique = true, length = 15)
    String numberPhone;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email must be at most 100 characters")
    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Size(max = 255, message = "Address must be at most 255 characters")
    @Column(name = "address")
    String address;

    @Column(name = "registration_date", nullable = false)
    LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "cardType", nullable = false)
    CardType cardType;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        if (registrationDate == null) {
            registrationDate = LocalDate.now();
        }
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
}