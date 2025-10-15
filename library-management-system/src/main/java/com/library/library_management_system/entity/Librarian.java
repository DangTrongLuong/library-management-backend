package com.library.library_management_system.entity;

import java.math.BigDecimal;

import com.library.library_management_system.enums.Shift;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
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
           @Index(name = "idx_librarian_shift", columnList = "shift") 
       })
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Librarian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer librarianId;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "shift", columnDefinition = "ENUM('Morning', 'Afternoon', 'Evenning', 'Fulltime')")
    Shift shift;

    @Column(name = "time_shift", length = 100)
    String timeShift;

    @DecimalMin(value = "0.0", message = "Salary must be >= 0")
    @Column(name = "salary", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    BigDecimal salary = BigDecimal.ZERO;

  
    
    @PrePersist
    @PreUpdate
    private void setTimeShift() {
        if (this.shift != null) {
            switch (this.shift) {
                case Morning -> this.timeShift = "8h-12h";
                case Afternoon -> this.timeShift = "13h-17h";
                case Evenning -> this.timeShift = "19h-23h";
                case Fulltime -> this.timeShift = "8h-17h";
                default -> this.timeShift = null;
            }
        } else {
            this.timeShift = null;
        }
    }
}