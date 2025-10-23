package com.library.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Books;
import com.library.library_management_system.entity.Borrow;
import com.library.library_management_system.enums.BorrowStatus;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String> {

    @Query("SELECT b FROM Borrow b JOIN FETCH b.reader JOIN FETCH b.book WHERE " +
           "b.reader.readerId LIKE CONCAT('%', :keyword, '%') OR " +
           "b.book.bookId LIKE CONCAT('%', :keyword, '%') OR " +
           "LOWER(b.reader.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.book.bookTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Borrow> searchBorrows(@Param("keyword") String keyword);

    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.reader = :reader AND b.status = :status")
    long countByReaderAndStatus(@Param("reader") Reader reader, @Param("status") BorrowStatus status);

    @Query("SELECT COUNT(b) FROM Borrow b WHERE b.book = :book AND b.status = :status")
    long countByBookAndStatus(@Param("book") Books book, @Param("status") BorrowStatus status);

    List<Borrow> findByReader(Reader reader);

    List<Borrow> findByBook(Books book);
}