package com.library.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Borrow;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, String> {

    
}