package com.library.library_management_system.repository.Librarian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Librarian.Shift;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
}
