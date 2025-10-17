package com.library.library_management_system.entity.Librarian;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Shift")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    Integer shiftId;

    @NotBlank(message = "Shift name is required")
    @Size(max = 50)
    @Column(name = "shift_name", nullable = false)
    String shiftName;

    @NotBlank(message = "Time shift is required")
    @Size(max = 100)
    @Column(name = "time_shift", nullable = false)
    String timeShift;

    @NotNull(message = "Hours per day is required")
    @DecimalMin(value = "0.0", message = "Hours per day must be >= 0")
    @Column(name = "hours_per_day", nullable = false)
    BigDecimal hoursPerDay;
}
