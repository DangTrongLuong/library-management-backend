package com.library.library_management_system.repository;
import com.library.library_management_system.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {
    // helper query methods (optional / may be unused by service)
    List<Report> findByReportTypeContainingIgnoreCase(String type);
    List<Report> findByCreator_Id(String creatorId);
    List<Report> findByStartDateBetween(LocalDate start, LocalDate end);
}
