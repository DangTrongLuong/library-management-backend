package com.library.library_management_system.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import com.library.library_management_system.entity.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByReaderCodeContainingAndBookCodeContaining(String readerCode, String bookCode, Sort sort);
}