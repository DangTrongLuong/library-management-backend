package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BookRepository extends JpaRepository<Books, Integer> {

    @Query("SELECT b FROM Books b WHERE " +
            "LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.category.typeName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Books> searchBooks(String keyword);
}
