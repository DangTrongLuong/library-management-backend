package com.library.library_management_system.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.library.library_management_system.enums.Shift;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
           @Index(name = "idx_librarian_shift", columnList = "shift"),
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

    @Enumerated(EnumType.STRING)
    @Column(name = "shift", columnDefinition = "ENUM('Morning', 'Afternoon', 'Evenning', 'Fulltime')")
    Shift shift;

    @Column(name = "time_shift", length = 100)
    String timeShift;

    @DecimalMin(value = "0.0", message = "Hourly wage must be >= 0")
    @Column(name = "hourly_wage", precision = 10, scale = 2)
    BigDecimal hourlyWage;

    @DecimalMin(value = "0.0", message = "Monthly salary must be >= 0")
    @Column(name = "salary", precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    BigDecimal salary = BigDecimal.ZERO;

    
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
        setTimeShiftAndCalculateSalary();
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        setTimeShiftAndCalculateSalary();
        updatedAt = LocalDate.now();
    }


    private void setTimeShiftAndCalculateSalary() {
        if (this.shift != null && this.hourlyWage != null) {
            BigDecimal hoursPerDay = BigDecimal.ZERO;
            
            switch (this.shift) {
                case Morning -> {
                    this.timeShift = "8h-12h";
                    hoursPerDay = new BigDecimal("4"); 
                }
                case Afternoon -> {
                    this.timeShift = "13h-17h";
                    hoursPerDay = new BigDecimal("4"); 
                }
                case Evenning -> {
                    this.timeShift = "19h-23h";
                    hoursPerDay = new BigDecimal("4");  
                }
                case Fulltime -> {
                    this.timeShift = "8h-17h";
                    hoursPerDay = new BigDecimal("8");  
                }
                default -> this.timeShift = null;
            }
            

            BigDecimal workDaysPerMonth = new BigDecimal("26");
            this.salary = this.hourlyWage
                    .multiply(hoursPerDay)
                    .multiply(workDaysPerMonth)
                    .setScale(2, java.math.RoundingMode.HALF_UP);
        } else {
            this.timeShift = null;
            this.salary = BigDecimal.ZERO;
        }
    }
}