package com.library.library_management_system.repository;

import com.library.library_management_system.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Books, Integer> {
}