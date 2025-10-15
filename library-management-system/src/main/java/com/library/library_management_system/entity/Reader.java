package com.library.library_management_system.entity;

import java.time.LocalDate;

import com.library.library_management_system.enums.CardType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
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
@Table(name = "Reader",
       indexes = {@Index(name = "idx_reader_phone", columnList = "number_phone"),
                  @Index(name = "idx_reader_email", columnList = "email")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer readerId;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    String name;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Invalid phone format")
    @Column(name = "number_phone", nullable = false, unique = true, length = 15)
    String numberPhone;

    @Email(message = "Invalid email format")
    @Size(max = 100)
    @Column(name = "email", unique = true)
    String email;

    @Size(max = 255)
    @Column(name = "address")
    String address;

    @Column(name = "registration_date")
    LocalDate registrationDate;

    @PrePersist
    protected void onCreate() {
    if (registrationDate == null) {
        registrationDate = LocalDate.now();
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", columnDefinition = "ENUM('Bronze', 'Silver', 'VIP') DEFAULT 'Bronze'")
    CardType cardType = CardType.Bronze;

    
}
