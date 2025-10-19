package com.library.library_management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {
    boolean existsByNumberPhoneAndReaderIdNot(String numberPhone, Integer readerId);
    boolean existsByEmailAndReaderIdNot(String email, Integer readerId);
    Optional<Reader> findByReaderId(Integer readerId);
}
