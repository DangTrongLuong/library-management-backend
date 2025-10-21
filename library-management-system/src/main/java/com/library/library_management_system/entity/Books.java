package com.library.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.library.library_management_system.customValidation.FutureOrPresentYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
}
