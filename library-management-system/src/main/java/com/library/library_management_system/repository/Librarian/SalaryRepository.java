package com.library.library_management_system.repository.Librarian;

import java.time.YearMonth;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.library_management_system.entity.Librarian.Librarian;
import com.library.library_management_system.entity.Librarian.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer> {
    Optional<Salary> findByLibrarianAndMonth(Librarian librarian, YearMonth month);
    Optional<Salary> findTopByLibrarianOrderByMonthDesc(Librarian librarian);
    void deleteByLibrarian(Librarian librarian);
}
