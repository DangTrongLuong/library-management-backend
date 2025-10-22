package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {

    boolean existsByNumberPhone(String numberPhone);

    boolean existsByEmail(String email);

    @Query("SELECT r FROM Reader r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "r.numberPhone LIKE CONCAT('%', :keyword, '%') OR " +
            "LOWER(r.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Reader> searchReaders(@Param("keyword") String keyword);
}
