package com.library.library_management_system.entity;

import com.library.library_management_system.customValidation.FutureOrPresentYear;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Books",
       indexes = {@Index(name = "idx_book_title", columnList = "book_title"),
                  @Index(name = "idx_author", columnList = "author")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bookId;

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
    Category category;

    @Size(max = 100)
    @Column(name = "NXB")
    String nxb;

    @Min(value = 0, message = "Quantity must be >= 0")
    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    Integer quantity = 0;

    @Size(max = 255, message = "Image URL too long")
    @Column(name = "image_url")
    String imageUrl;
}
