package com.library.library_management_system.repository.Librarian;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Librarian.Librarian;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, String> {

    @Query("SELECT l FROM Librarian l WHERE (:keyword IS NULL OR LOWER(l.librarianName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR l.phone LIKE CONCAT('%', :keyword, '%') OR LOWER(l.librarianId) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Librarian> searchByNameOrPhoneOrId(@Param("keyword") String keyword, Pageable pageable);
}