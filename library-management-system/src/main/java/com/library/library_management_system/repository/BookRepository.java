package com.library.library_management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, String> {

    @Query("SELECT b FROM Books b WHERE " +
            "LOWER(b.bookTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(b.category.typeName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Books> searchBooks(@Param("keyword") String keyword);

    boolean existsByBookTitle(String bookTitle);
}