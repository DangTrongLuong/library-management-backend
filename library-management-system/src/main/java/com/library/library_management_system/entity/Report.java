package com.library.library_management_system.entity;

import java.time.LocalDate;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report {

    @Id
    @Column(name = "report_id", nullable = false, unique = true, length = 9)
    String reportId;

    @NotNull(message = "Report type is required")
    @Column(name = "report_type", nullable = false, length = 50)
    String reportType;

    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    LocalDate endDate;

    @Column(name = "content", columnDefinition = "TEXT")
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @NotNull(message = "Creator is required")
    User creator;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDate createdAt;

    @Column(name = "updated_at")
    LocalDate updatedAt;

    // Validate creator is admin before persisting
    @PrePersist
    protected void onCreate() {
        if (creator == null) {
            throw new IllegalStateException("Creator is required");
        }
        if (!isAdminCreator()) {
            throw new SecurityException("Only admin users can create reports");
        }
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    // Helper: try to detect an admin role using reflection (supports getRole() or a 'role' field)
    private boolean isAdminCreator() {
        try {
            Method m = creator.getClass().getMethod("getRole");
            Object role = m.invoke(creator);
            if (role == null) return false;
            return "ADMIN".equalsIgnoreCase(role.toString());
        } catch (NoSuchMethodException e) {
            try {
                Field f = creator.getClass().getDeclaredField("role");
                f.setAccessible(true);
                Object role = f.get(creator);
                if (role == null) return false;
                return "ADMIN".equalsIgnoreCase(role.toString());
            } catch (Exception ex) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}