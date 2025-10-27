package com.library.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.library.library_management_system.customValidation.FutureOrPresentYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "books",
        indexes = {
                @Index(name = "idx_book_title", columnList = "book_title"),
                @Index(name = "idx_author", columnList = "author")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class Books {

    @Id
    @Size(max = 10, message = "Book ID must be at most 10 characters")
    @Column(name = "book_id", nullable = false, unique = true, length = 10)
    String bookId;

    @NotBlank(message = "Book title is required")
    @Size(max = 255)
    @Column(name = "book_title", nullable = false)
    String bookTitle;

    @NotBlank(message = "Author is required")
    @Size(max = 100)
    @Column(name = "author", nullable = false)
    String author;

    @Min(value = 1500, message = "Publication year must be >= 1500")
    @FutureOrPresentYear
    @Column(name = "publication_year")
    Integer publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    @Size(max = 100)
    @Column(name = "nxb")
    String nxb;

    @Min(value = 0, message = "Quantity must be >= 0")
    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    Integer quantity = 0;

    @Size(max = 255, message = "Image URL too long")
    @Column(name = "image_url")
    String imageUrl;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    BigDecimal price;

    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
