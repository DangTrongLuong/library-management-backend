package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Books, Integer> {
    @Query("SELECT b FROM Books b " +
            "WHERE (:title IS NULL OR LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) " +
            "AND (:categoryId IS NULL OR b.category.categoryId = :categoryId)")
    List<Books> searchBooks(@Param("title") String title,
                            @Param("author") String author,
                            @Param("categoryId") Integer categoryId);
}