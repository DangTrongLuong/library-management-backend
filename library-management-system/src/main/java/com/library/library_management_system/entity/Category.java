package com.library.library_management_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Category",
       indexes = {@Index(name = "idx_type_name", columnList = "type_name"),
                  @Index(name = "idx_shelf_position", columnList = "shelf_position")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer categoryId;

    @NotBlank(message = "Type name is required")
    @Size(max = 100)
    @Column(name = "type_name", nullable = false, unique = true)
    String typeName;

    @Column(columnDefinition = "TEXT")
    String description;

    @NotBlank(message = "Shelf position is required")
    @Size(max = 50)
    @Column(name = "shelf_position", nullable = false, unique = true)
    String shelfPosition;

    @Column(columnDefinition = "TEXT")
    String note;
}
