package com.library.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "Category",
        indexes = {
                @Index(name = "idx_type_name", columnList = "type_name"),
                @Index(name = "idx_shelf_position", columnList = "shelf_position")
        })
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

    @OneToMany(mappedBy = "category")
    @JsonBackReference
    private List<Books> books;

    // ✅ Constructor 5 tham số (không có List<Books>)
    public Category(Integer categoryId, String typeName, String description, String shelfPosition, String note) {
        this.categoryId = categoryId;
        this.typeName = typeName;
        this.description = description;
        this.shelfPosition = shelfPosition;
        this.note = note;
    }
}