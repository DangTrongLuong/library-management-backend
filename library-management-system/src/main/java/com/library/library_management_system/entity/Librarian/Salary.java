package com.library.library_management_system.entity.Librarian;

import java.math.BigDecimal;
import java.time.YearMonth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Salary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    Integer salaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "librarian_id", nullable = false)
    Librarian librarian;

    @NotNull(message = "Hourly wage is required")
    @DecimalMin(value = "0.0", message = "Hourly wage must be >= 0")
    @Column(name = "hourly_wage", precision = 10, scale = 2)
    BigDecimal hourlyWage;

    @NotNull(message = "Month is required")
    @Column(name = "month")
    YearMonth month;

    @NotNull(message = "Total salary is required")
    @DecimalMin(value = "0.0", message = "Total salary must be >= 0")
    @Column(name = "total_salary", precision = 10, scale = 2)
    BigDecimal totalSalary;
}
